package com.aakashbista.note.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatedDate(localDateTime: LocalDateTime):String{
    val dateTimeFormatter= DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a")
    return localDateTime.format(dateTimeFormatter)

}