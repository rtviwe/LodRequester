package com.example.roman.lodaddplaction.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "Request")
data class Request(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "request_id")
        var id: Int,
        var title: String,
        var description: String?,
        var dormitory: Dormitory?,
        @Embedded
        var user: User?
) : Serializable {

    @Ignore
    constructor(
            title: String,
            description: String,
            dormitory: Dormitory?,
            user: User?) : this(0, title, description, dormitory, user)
}
