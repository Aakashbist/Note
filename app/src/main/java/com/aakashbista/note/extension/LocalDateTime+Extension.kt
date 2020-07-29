package com.aakashbista.note.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatDate():String{
    val dateTimeFormatter= DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")
    return this.format(dateTimeFormatter)
}