package com.aakashbista.note.extension

import androidx.work.Data
import com.aakashbista.note.db.Reminder
import java.time.Duration
import java.time.LocalDateTime

fun Reminder.createDate(): Data {
    return Data.Builder()
        .putString("TITLE_KEY", this.title)
        .putString("DESCRIPTION_KEY", this.description)
        .build()
}

fun Reminder.millisToNotify(): Long = Duration.between(
    LocalDateTime.now(),
    this.dateTime
).toMillis()