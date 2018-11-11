package com.example.roman.lodaddplaction.createRequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roman.lodaddplaction.database.request.RequestDatabase
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateRequestViewModel(app: Application) : AndroidViewModel(app) {

    private val requestDao = RequestDatabase.getInstance(app).requestWithTagsDao()
    private val addRequestAndTagsJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + addRequestAndTagsJob)

    override fun onCleared() {
        super.onCleared()
        addRequestAndTagsJob.cancel()
    }

    fun addRequestAndTags(request: Request, tags: List<Tag>) {
        viewModelScope.launch {
            val id = requestDao.insertRequest(request)
            tags.forEach {
                it.requestId = id
                requestDao.insertTag(it)
            }
        }
    }
}