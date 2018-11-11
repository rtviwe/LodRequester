package com.example.roman.lodaddplaction.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.data.findDistance
import com.example.roman.lodaddplaction.data.observableToUpdateLocation
import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.detail.RequestDetailsActivity
import com.example.roman.lodaddplaction.model.Tag
import com.example.roman.lodaddplaction.model.TagType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_request.view.*
import java.util.*

class RequestAdapter(
    private val context: Context,
    private var data: List<RequestWithTags>,
    private val location: Location?
) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_request, parent, false)
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(newData: List<RequestWithTags>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val container = itemView.container_request!!
        private val ivAvatar = itemView.iv_avatar!!
        private val tvRequestTitle = itemView.tv_request_title!!
        private val cgTags = itemView.cg_tags!!
        private val expandButton = itemView.expand_button!!
        private val tvDescription = itemView.tv_description!!
        private val tvDistance = itemView.tv_distance!!
        private var locationDisposable: Disposable? = null

        fun bind(requestWithTags: RequestWithTags) {
            val (request, tags) = requestWithTags
            val (_, title, description, _, user) = request

            // Чтобы аватар был круглым
            ivAvatar.clipToOutline = true

            tvRequestTitle.text = title
            tvDescription.text = description
            user?.let {
                val latitude = it.latitude
                val longitude = it.longitude

                if (locationDisposable != null) {
                    locationDisposable?.dispose()
                }

                setUpLocation(latitude, longitude)
                locationDisposable = observableToUpdateLocation().subscribe {
                    setUpLocation(latitude, longitude)
                }
            }

            tags?.let {
                setUpTags(tags)
            }

            setUpExpandButton()

            Glide.with(context)
                .load(user?.avatarUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_sentiment_satisfied_black_24dp))
                .into(ivAvatar)

            container.setOnClickListener {
                Intent(context, RequestDetailsActivity::class.java).apply {
                    putExtra(RequestDetailsActivity.REQUEST_EXTRA, request)
                    context.startActivity(this)
                }
            }
        }

        private fun setUpTags(tags: List<Tag>) {
            cgTags.removeAllViews()

            tags.forEach {
                val chip = Chip(cgTags.context).apply {
                    @RequiresApi(Build.VERSION_CODES.M)
                    when (it.type) {
                        TagType.MONEY ->
                            setChipDrawable(ChipDrawable.createFromResource(context, R.xml.money_chip))
                        TagType.DEFAULT ->
                            setChipDrawable(ChipDrawable.createFromResource(context, R.xml.default_chip))
                    }

                    text = it.body
                }
                cgTags.addView(chip)
            }
        }

        private fun setUpExpandButton() {
            expandButton.setOnClickListener {
                when (tvDescription.visibility) {
                    View.VISIBLE -> {
                        it.animate().rotation(0f).start()
                        tvDescription.visibility = View.GONE
                    }
                    View.GONE -> {
                        it.animate().rotation(-180f).start()
                        tvDescription.visibility = View.VISIBLE
                    }
                }
            }
        }

        private fun setUpLocation(latitude: Double?, longitude: Double?) {
            if (latitude != null && longitude != null && location != null) {
                val distance = findDistance(
                    latitude,
                    longitude,
                    location.latitude,
                    location.longitude
                )
                if (distance < 1000)
                    tvDistance.text = String.format(Locale.US, "%.2f", distance).plus("m")
                else
                    tvDistance.text = String.format(Locale.US, "%.2f", distance / 1000).plus("km")
            } else {
                tvDistance.text = ""
            }
        }
    }
}
