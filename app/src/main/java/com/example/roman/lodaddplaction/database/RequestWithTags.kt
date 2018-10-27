package com.example.roman.lodaddplaction.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag

data class RequestWithTags(
        @Embedded
        var request: Request,
        @Relation(parentColumn = "request_id", entityColumn = "to_request_id")
        var tags: List<Tag>?)
