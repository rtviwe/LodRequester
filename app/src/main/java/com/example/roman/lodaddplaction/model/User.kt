package com.example.roman.lodaddplaction.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "user_id")
        var id: Int,
        var name: String,
        var password: String,
        var avatarUrl: String?
) : Serializable {

    @Ignore
    constructor(
            name: String,
            password: String,
            avatarUrl: String) : this(0, password, name, avatarUrl)
}
