package com.example.roman.lodaddplaction.data

import com.example.roman.lodaddplaction.database.RequestWithTags
import io.reactivex.Flowable

interface RequestProvider {

    fun getRequests(): Flowable<List<RequestWithTags>>
}
