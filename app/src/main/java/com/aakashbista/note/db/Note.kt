package com.aakashbista.note.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    val title: String,
    val note: String
): Parcelable {
    @PrimaryKey(autoGenerate = true)
   var id: Int = 0
}