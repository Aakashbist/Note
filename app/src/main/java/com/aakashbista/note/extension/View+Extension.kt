package com.aakashbista.note.extension

import android.view.View
import androidx.core.content.ContextCompat

fun View.changeColor(newColor: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}