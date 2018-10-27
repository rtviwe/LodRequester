package com.example.roman.lodaddplaction.database.request

import androidx.room.*
import com.example.roman.lodaddplaction.database.RequestWithTags
import com.example.roman.lodaddplaction.model.Request
import com.example.roman.lodaddplaction.model.Tag
import io.reactivex.Flowable

@Dao
interface RequestWithTagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRequest(request: Request): Long

    @Update
    fun updateRequest(request: Request)

    @Delete
    fun deleteRequest(request: Request)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTags(tags: List<Tag>)

    @Update
    fun updateTag(tag: Tag)

    @Delete
    fun deleteTag(tag: Tag)

    @Transaction
    @Query("SELECT * FROM request")
    fun getAllRequests(): Flowable<List<RequestWithTags>>

    @Transaction
    @Query("SELECT * FROM request WHERE request_id = :id")
    fun getRequestById(id: Int): Flowable<RequestWithTags>

    @Transaction
    @Query("SELECT * FROM request WHERE title LIKE :title")
    fun getRequestsByTitle(title: String): Flowable<List<RequestWithTags>>
}