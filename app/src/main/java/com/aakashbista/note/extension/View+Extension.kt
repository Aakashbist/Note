package com.aakashbista.note.extension

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat

fun View.changeColor(newColor: Int) {
    backgroundTintList = ColorStateList.valueOf(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}