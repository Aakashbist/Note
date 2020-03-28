package com.aakashbista.note.UI.Helper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private  var job= Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


}