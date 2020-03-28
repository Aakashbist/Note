package com.aakashbista.note.ui.Extension

import android.content.Context
import android.widget.Toast

fun Context.toast(message:String)= Toast.makeText(this,message,Toast.LENGTH_LONG).show()
