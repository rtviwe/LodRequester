package com.example.roman.lodaddplaction.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roman.lodaddplaction.data.RequestProvider
import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.database.request.RequestDatabase
import com.example.roman.lodaddplaction.model.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class RequestViewModel(app: Application) : AndroidViewModel(app), RequestProvider {

    private val requestWithUserDao = RequestDatabase.getInstance(app).requestWithTagsDao()

    override fun getRequests(): Flowable<List<RequestWithTags>> = requestWithUserDao.getAllRequests()

    // TODO make search
    fun getRequestsByTitle(title: String): Flowable<List<RequestWithTags>> = if (title.isNotBlank()) requestWithUserDao.getRequestsByTitle("'%$title%'")
    else requestWithUserDao.getAllRequests()

    // TODO deserialize
    fun addRequestFromString(jsonString: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val requestType = object : TypeToken<Request>() {}.type
            val request = Gson().fromJson<Request>(jsonString, requestType)
            requestWithUserDao.insertRequest(request)
        }
    }
}