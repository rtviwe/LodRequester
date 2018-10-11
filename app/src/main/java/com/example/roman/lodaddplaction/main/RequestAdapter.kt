package com.example.roman.lodaddplaction.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.detail.RequestDetailsActivity
import com.example.roman.lodaddplaction.models.RequestModel
import com.example.roman.lodaddplaction.models.TagType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.item_request.view.*

class RequestAdapter(
        private val context: Context,
        private val list: List<RequestModel>
) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_request, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            tvRequestTilte.text = list[position].title

            // Чтобы аватар был круглым
            ivAvatar.clipToOutline = true

            cgTags.removeAllViews()

            list[position].tags?.forEach {
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

        Glide.with(context)
                .load(list[position].user?.avatarUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_sentiment_satisfied_black_24dp))
                .into(holder.ivAvatar)

        holder.container.setOnClickListener {
            Intent(context, RequestDetailsActivity::class.java).apply {
                putExtra(RequestDetailsActivity.DATA_EXTRA, list[position])
                context.startActivity(this)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivAvatar = itemView.iv_avatar
        val tvRequestTilte = itemView.tv_request_title
        val cgTags = itemView.cg_tags
        val container = itemView.container_request
    }
}