package com.aakashbista.note.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*

@Entity
@Parcelize
data class Reminder(
    val title: String,
    var description:String,
    val dateTime:LocalDateTime,
    val workRequestId:String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}