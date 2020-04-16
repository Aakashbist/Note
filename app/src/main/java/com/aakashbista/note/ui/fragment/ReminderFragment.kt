package com.aakashbista.note.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.ui.navigation.NavigationFragment
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment(), NavigationFragment, MenuItem.OnMenuItemClickListener{



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar, menu)
        val addReminderMenu = menu.findItem(R.id.appbar_addReminder)
        super.onCreateOptionsMenu(menu, inflater)

        addReminderMenu.setOnMenuItemClickListener(this)

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.appbar_addReminder -> {
                showAddReminderDialog()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun showAddReminderDialog() {
        ReminderFragmentDirections.openReminder().navigateSafe()
    }

}



