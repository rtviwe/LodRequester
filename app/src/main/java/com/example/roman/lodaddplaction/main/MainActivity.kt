package com.example.roman.lodaddplaction.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.createRequest.CreateRequestActivity
import com.example.roman.lodaddplaction.data.findDistance
import com.example.roman.lodaddplaction.data.getLocation
import com.example.roman.lodaddplaction.model.TagType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var requestAdapter: RequestAdapter
    private var adapterDisposable: Disposable? = null
    private var isFilteredByMoney = false
    private var isFilteredByLocation = false

    companion object {

        private const val REQUEST_PERMISSION = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION), REQUEST_PERMISSION)
        }

        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel::class.java)

        fab.setOnClickListener {
            Intent(baseContext, CreateRequestActivity::class.java).apply {
                baseContext.startActivity(this)
            }
        }

        button_filter_money.setOnClickListener {
            isFilteredByMoney = !isFilteredByMoney
            if (isFilteredByLocation and isFilteredByMoney)
                isFilteredByLocation = !isFilteredByLocation
            handleChangeFilters()
        }

        button_filter_distance.setOnClickListener {
            isFilteredByLocation = !isFilteredByLocation
            if (isFilteredByLocation and isFilteredByMoney)
                isFilteredByMoney = !isFilteredByMoney
            handleChangeFilters()
        }

        bottom_app_bar.background = getDrawable(R.drawable.bottom_app_bar_background)
        readToken()

        assets.open("testFile.json").bufferedReader().use {
            requestViewModel.addRequestsFromString(it.readLine().trim())
        }
    }

    override fun onResume() {
        super.onResume()

        requestAdapter = RequestAdapter(this, listOf(), getLocation(baseContext))

        attachRequestsToAdapter()

        rv_requests.apply {
            adapter = requestAdapter
            layoutManager = LinearLayoutManager(context)
        }

        initSearching()
        handleOnSearchTextChanged(et_searchField.text.toString())
        handleChangeFilters()
    }

    override fun onStop() {
        super.onStop()
        adapterDisposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottomappbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (et_searchField.hasFocus())
            et_searchField.clearFocus()
        else
            super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (grantResults[0] == PERMISSION_GRANTED) {
                    getLocation(baseContext)
                }
            }
            else -> {
                Log.wtf("MainActivity", "Unknown permission")
            }
        }
    }

    private fun initSearching() {
        et_searchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(title: CharSequence?, start: Int, before: Int, count: Int) {
                handleOnSearchTextChanged(title.toString())
            }
        })
    }

    private fun attachRequestsToAdapter() {
        adapterDisposable = requestViewModel.getRequests()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                requestAdapter.setData(it)
            }
    }

    private fun handleOnSearchTextChanged(text: String) {
        adapterDisposable = requestViewModel.getRequestsByTitle(text)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tags ->
                requestAdapter.setData(tags)
            }
    }

    private fun handleChangeFilters() {
        adapterDisposable = requestViewModel.getRequests()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { requests ->
                val sortedRequests = when {
                    isFilteredByMoney -> requests.sortedWith(compareBy {
                        it.tags?.firstOrNull { tag -> tag.type == TagType.MONEY }?.body?.toInt()
                    })
                    isFilteredByLocation -> requests.sortedWith(compareBy {
                        it.request.user?.let { user ->
                            val location = getLocation(baseContext)
                            val latitude = user.latitude
                            val longitude = user.longitude
                            if (location != null && latitude != null && longitude != null)
                                findDistance(
                                    latitude,
                                    longitude,
                                    location.latitude,
                                    location.longitude
                                ).roundToInt()
                            else 0
                        }
                    })
                    else -> requests
                }
                requestAdapter.setData(sortedRequests)
            }
    }

    private fun readToken() {
        val token = getSharedPreferences("default", Context.MODE_PRIVATE)
            .getString("accessToken", "")

        Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
    }
}
