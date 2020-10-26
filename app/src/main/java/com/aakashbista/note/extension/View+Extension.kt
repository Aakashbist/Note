package com.aakashbista.note.extension

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.aakashbista.note.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.notes_fragment.view.*


fun View.changeColor(newColor: Int) {
    backgroundTintList = ColorStateList.valueOf(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}

fun View.snackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(this.context, R.color.colorAccent))
    snackbar.anchorView =btn_add
    snackbar.show()
}
