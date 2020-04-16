package com.aakashbista.note.ui.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.adapter.ReminderAdapter
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_reminder.*

class ReminderFragment : Fragment(), NavigationFragment, MenuItem.OnMenuItemClickListener {


    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var viewModel: ReminderViewModel

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


        reminderRecyclerView.setHasFixedSize(true)
        reminderRecyclerView.layoutManager = LinearLayoutManager(context)
        reminderAdapter = ReminderAdapter { reminder, isChecked ->

            if (isChecked) {
                //set alarm after checking
            } else {

                WorkManager.getInstance(context!!)
                    .cancelAllWorkByTag("${reminder.title}${reminder.description}")
            }

//           WorkManager.getInstance(context!!)
//                .getWorkInfosByTagLiveData("${reminder.title}${reminder.description}")
//                .observe(viewLifecycleOwner, Observer { it: MutableList<WorkInfo>? ->
//
//
//                })
        }
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


}



