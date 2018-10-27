package com.example.roman.lodaddplaction.createRequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.roman.lodaddplaction.database.request.RequestDatabase
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch

class CreateRequestViewModel(app: Application) : AndroidViewModel(app) {

    private val requestDao = RequestDatabase.getInstance(app).requestWithTagsDao()

    fun addRequestAndTags(request: Request, tags: List<Tag>) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = requestDao.insertRequest(request)
            bindTagsToRequest(tags, id)
            requestDao.insertTags(tags)
        }
    }

    private fun bindTagsToRequest(tags: List<Tag>, requestId: Long) {
        tags.forEach {
            it.requestId = requestId
        }
    }
}