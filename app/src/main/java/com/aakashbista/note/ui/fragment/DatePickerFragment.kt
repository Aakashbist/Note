package com.aakashbista.note.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.aakashbista.note.ui.navigation.NavigationFragment
import java.time.LocalDateTime

class DatePickerFragment : DialogFragment(), NavigationFragment,
    DatePickerDialog.OnDateSetListener {


    private var localDateTime: LocalDateTime = LocalDateTime.now()
    private var year: Int = 0
    private var month: Int = 0
    private var dayOfMonth: Int = 0
    private var hourOfDay: Int = 0
    private var minute: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        localDateTime = DatePickerFragmentArgs.fromBundle(arguments!!).dateTime!!

        if (localDateTime != null) {
            setDateTimeInCalender()

        } else {
            setDateTimeInCalender()
        }

        return requireContext()?.let {
            DatePickerDialog(
                it,
                this,
                year,
                month,
                dayOfMonth
            )
        }!!
    }

    private fun setDateTimeInCalender() {
        localDateTime?.let {
            year = it.year
            month = it.monthValue
            dayOfMonth = it.dayOfMonth
            hourOfDay = it.hour
            minute = it.minute
        }

//        year = calender.get(Calendar.YEAR)
//        month = calender.get(Calendar.MONTH)
//        dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
//        hourOfDay = calender.get(Calendar.HOUR_OF_DAY)
//        minute = calender.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        DatePickerFragmentDirections.openTimePicer(
            data = TimePickerData(
                year,
                month,
                dayOfMonth,
                hourOfDay,
                minute
            )
        ).navigateSafe()

    }


}




