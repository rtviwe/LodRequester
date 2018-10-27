package com.example.roman.lodaddplaction.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.model.Request

class RequestDetailsActivity : AppCompatActivity() {

    private lateinit var requestModel: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        requestModel = intent.getSerializableExtra(REQUEST_EXTRA) as Request
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    companion object {

        const val REQUEST_EXTRA = "request_extra_data"
    }
}
