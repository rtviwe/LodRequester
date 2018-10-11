package com.example.roman.lodaddplaction.models

import java.io.Serializable

data class RequestModel (
        val title: String,
        val tags: List<Tag>?,
        val description: String,
        val dormitory: Dormitory?,
        val user: UserModel?
) : Serializable