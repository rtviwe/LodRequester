package com.example.roman.lodaddplaction.models

import java.io.Serializable

data class Tag(
        var body: String,
        var type: TagType = TagType.DEFAULT
) : Serializable