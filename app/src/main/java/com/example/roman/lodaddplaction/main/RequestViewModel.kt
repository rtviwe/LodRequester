package com.example.roman.lodaddplaction.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roman.lodaddplaction.data.RequestProvider
import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.database.request.RequestDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RequestViewModel(app: Application) : AndroidViewModel(app), RequestProvider {

    private val requestWithUserDao = RequestDatabase.getInstance(app).requestWithTagsDao()
    private val addRequestsJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + addRequestsJob)

    override fun onCleared() {
        super.onCleared()
        addRequestsJob.cancel()
    }

    override fun getRequests(): Flowable<List<RequestWithTags>> = requestWithUserDao.getAllRequests()

    fun getRequestsByTitle(title: String): Flowable<List<RequestWithTags>> =
        if (title.isNotBlank())
            requestWithUserDao.getRequestsByTitle("%$title%")
        else
            requestWithUserDao.getAllRequests()

    fun addRequestsFromString(jsonString: String) {
        viewModelScope.launch {
            val requestType = object : TypeToken<List<RequestWithTags>>() {}.type
            val requests = Gson().fromJson<List<RequestWithTags>>(jsonString, requestType)
            requests.forEach {
                requestWithUserDao.insertRequest(it.request)
                it.tags?.forEach { tag -> requestWithUserDao.insertTag(tag) }
            }
        }
    }
}