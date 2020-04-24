package com.aakashbista.note.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Entity
@Parcelize
data class Reminder(
    val title: String,
    var description: String,
    val dateTime: LocalDateTime,
    val workRequestId: String?=null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
val isEnabled : Boolean
    get() = workRequestId!=null

}
