package com.aakashbista.note.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
//
//class DateConverter {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time?.toLong()
//    }
//}
class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.systemDefault()) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneOffset.systemDefault())?.toInstant()?.toEpochMilli()
    }
}