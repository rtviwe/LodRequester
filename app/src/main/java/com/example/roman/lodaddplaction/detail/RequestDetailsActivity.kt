package com.example.roman.lodaddplaction.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roman.lodaddplaction.R
import com.example.roman.lodaddplaction.models.RequestModel

class RequestDetailsActivity : AppCompatActivity() {

    private lateinit var requestModel: RequestModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        requestModel = intent.getSerializableExtra(DATA_EXTRA) as RequestModel
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    companion object {

        const val DATA_EXTRA = "data"
    }
}
