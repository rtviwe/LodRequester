package com.example.roman.lodaddplaction.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.createRequest.CreateRequestActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var requestAdapter: RequestAdapter
    private var disposableAdapter: Disposable? = null
    private var disposableSearching: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel::class.java)

        fab.setOnClickListener {
            Intent(baseContext, CreateRequestActivity::class.java).apply {
                baseContext.startActivity(this)
            }
        }

        bottom_app_bar.background = getDrawable(R.drawable.bottom_app_bar_background)
        readToken()

        assets.open("testFile.json").bufferedReader().use {
            requestViewModel.addRequestFromString(it.readLine().trim())
        }
    }

    override fun onResume() {
        super.onResume()

        requestAdapter = RequestAdapter(this, listOf())

        disposableAdapter = requestViewModel.getRequests()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    requestAdapter.setData(it)
                }

        rv_requests.apply {
            adapter = requestAdapter
            layoutManager = LinearLayoutManager(context)
        }

        initSearching()
        handleOnSearchTextChanged(et_searchField.text.toString())
    }

    override fun onStop() {
        super.onStop()
        disposableAdapter?.dispose()
        disposableSearching?.dispose()
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

    private fun initSearching() {
        et_searchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(title: CharSequence?, start: Int, before: Int, count: Int) {
                handleOnSearchTextChanged(title.toString())
            }
        })
    }

    private fun handleOnSearchTextChanged(text: String) {
        disposableSearching = requestViewModel.getRequestsByTitle(text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    requestAdapter.setData(it)
                }
    }

    private fun readToken() {
        val token = getSharedPreferences("default", Context.MODE_PRIVATE)
                .getString("accessToken", "")

        Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
    }
}
