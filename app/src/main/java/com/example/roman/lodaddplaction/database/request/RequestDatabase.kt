package com.example.roman.lodaddplaction.database.request

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roman.lodaddplaction.database.SingletonHolder
import com.example.roman.lodaddplaction.database.converters.DormitoryTypeConverter
import com.example.roman.lodaddplaction.database.converters.TagTypeConverter
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag
import com.example.roman.lodaddplaction.model.User

@Database(entities = [Request::class, User::class, Tag::class], version = 1, exportSchema = false)
@TypeConverters(DormitoryTypeConverter::class, TagTypeConverter::class)
abstract class RequestDatabase : RoomDatabase() {

    companion object : SingletonHolder<RequestDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            RequestDatabase::class.java,
            "Request.db"
        )
            .build()
    })

    abstract fun requestWithTagsDao(): RequestWithTagsDao
}
