package com.aakashbista.note.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.aakashbista.note.extension.getParentFragmentListener
import com.aakashbista.note.ui.navigation.NavigationFragment
import java.util.*

class DatePickerFragment : DialogFragment() ,NavigationFragment,DatePickerDialog.OnDateSetListener,TimePickerFragment.TimeSetListener{

    interface Listener  {
        fun onDateSet(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    }

    val parent: Listener
        get() = getParentFragmentListener<Listener>()
    private var timePickerDialog :TimePickerDialog?=null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val now = Calendar.getInstance()

        var year = now.get(Calendar.YEAR)
        var month = now.get(Calendar.MONTH)
        var dayOfMonth = now.get(Calendar.DAY_OF_MONTH)
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)


        arguments?.let {
            var date = DatePickerFragmentArgs.fromBundle(it).date.toString()

            if (date != "") {
                val split = date.split("/").toTypedArray()
                dayOfMonth = Integer.valueOf(split[2])
                month = Integer.valueOf(split[1])
                year = Integer.valueOf(split[0])
            }
        }


        return requireContext()?.let {
            DatePickerDialog(
                it,
                this ,
                year,
                month,
                dayOfMonth
            )

        }!!
    }

    private fun setDateTime(
        year: Int,
        month: Int,
        day: Int,
        hourOfDay: Int,
        minute: Int
    ) {
        parent.onDateSet(year, month, day, hourOfDay, minute)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            DatePickerFragmentDirections.openTimePicer(data = TimePickerData(year,month,dayOfMonth)).navigateSafe()
    }


    override fun onTimeSet(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        parent.onDateSet(year,month, day, hour, minute)
    }


}




