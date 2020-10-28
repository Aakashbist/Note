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

    private  var localDateTime: LocalDateTime? = null
    private var year: Int = 0
    private var month: Int = 0
    private var dayOfMonth: Int = 0
    private var hourOfDay: Int = 0
    private var minute: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        localDateTime = DatePickerFragmentArgs.fromBundle(requireArguments()).dateTime

        if (localDateTime != null) {
            setDateTimeInCalender()

        } else {
            localDateTime= LocalDateTime.now()
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
        }
    }

    private fun setDateTimeInCalender() {
        localDateTime?.let {
            year = it.year
            month = it.monthValue
            dayOfMonth = it.dayOfMonth
            hourOfDay = it.hour
            minute = it.minute
        }
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