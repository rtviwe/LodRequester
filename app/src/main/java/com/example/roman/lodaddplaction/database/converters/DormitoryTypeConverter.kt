package com.example.roman.lodaddplaction.database.converters

import androidx.room.TypeConverter
import com.example.roman.lodaddplaction.model.Dormitory

class DormitoryTypeConverter {

    @TypeConverter
    fun toInt(dormitory: Dormitory) = dormitory.ordinal

    @TypeConverter
    fun toDormitory(id: Int) = Dormitory.values().first { it.ordinal == id }
}