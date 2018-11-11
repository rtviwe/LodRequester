package com.example.roman.lodaddplaction.database.converters

import androidx.room.TypeConverter
import com.example.roman.lodaddplaction.model.TagType

class TagTypeConverter {

    @TypeConverter
    fun toInt(tagType: TagType) = tagType.ordinal

    @TypeConverter
    fun toTagType(id: Int) = TagType.values().first { it.ordinal == id }
}
