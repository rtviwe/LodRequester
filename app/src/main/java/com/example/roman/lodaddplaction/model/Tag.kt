package com.example.roman.lodaddplaction.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Tag(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    var id: Int,
    var body: String,
    var type: TagType = TagType.DEFAULT,
    @ColumnInfo(name = "to_request_id")
    var requestId: Long
) : Serializable {

    @Ignore
    constructor(
        body: String,
        type: TagType
    ) : this(0, body, type, 0)
}
