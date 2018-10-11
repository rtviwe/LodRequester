package com.example.roman.lodaddplaction.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.createRequest.CreateRequestActivity
import com.example.roman.lodaddplaction.data.MockRequestProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var requestAdapter: RequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        fab.setOnClickListener {
            Intent(baseContext, CreateRequestActivity::class.java).apply {
                baseContext.startActivity(this)
            }
        }

        bottom_app_bar.background = getDrawable(R.drawable.bottom_app_bar_background)
    }

    override fun onResume() {
        super.onResume()

        requestAdapter = RequestAdapter(this, MockRequestProvider.getRequests())

        rv_requests.apply {
            adapter = requestAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
}
