package com.aakashbista.note.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.adapter.ReminderAdapter
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_reminder.*
import java.util.*

class ReminderFragment : Fragment(), NavigationFragment, MenuItem.OnMenuItemClickListener,
    ReminderAdapter.ReminderActionListener {


    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)


        reminderRecyclerView.setHasFixedSize(true)
        reminderRecyclerView.layoutManager = LinearLayoutManager(context)
        reminderAdapter = ReminderAdapter(this)
        reminderRecyclerView.adapter = reminderAdapter
        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        viewModel.reminders.observe(viewLifecycleOwner, Observer { reminders ->
            reminders?.let {
                reminderAdapter.setReminder(reminders)
            }
        })
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

    override fun reminderOpen(reminder: Reminder) {
        ReminderFragmentDirections.openReminder(reminder).navigateSafe()
    }

    override fun reminderStateChanged(reminder: Reminder, state: ReminderAdapter.ReminderState) {
        val uuid: UUID = UUID.fromString(reminder.workRequestId)
        when (state) {

            ReminderAdapter.ReminderState.ENABLE -> {
                Log.d("in ", "xxxx")
            }
            ReminderAdapter.ReminderState.DISABLE -> {
                WorkManager.getInstance(context!!)
                    .getWorkInfoByIdLiveData(uuid)
                    .observe(viewLifecycleOwner,
                        Observer { it: WorkInfo? ->
                            if (it != null) {
                                if (it.state == WorkInfo.State.ENQUEUED)
                                    WorkManager.getInstance(context!!).cancelWorkById(uuid)
                            }
                        })
            }

        }
    }


}



