package com.aakashbista.note.extension

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.aakashbista.note.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*

fun View.changeColor(newColor: Int) {
    backgroundTintList = ColorStateList.valueOf(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Undo") {
            it()
        }
    }
    snackbar.anchorView = bottom_navigation
    snackbar.setTextColor(ContextCompat.getColor(context,R.color.white))
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
    snackbar.show()
}