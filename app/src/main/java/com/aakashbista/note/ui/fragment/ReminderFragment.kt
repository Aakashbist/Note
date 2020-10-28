

package com.aakashbista.note.ui.fragment
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.aakashbista.note.R
import com.aakashbista.note.adapter.ReminderAdapter
import com.aakashbista.note.appManager.NotificationWorker
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.extension.createDate
import com.aakashbista.note.extension.millisToNotify
import com.aakashbista.note.extension.snackbar
import com.aakashbista.note.ui.Extension.toast
import com.aakashbista.note.ui.navigation.NavigationFragment
import com.aakashbista.note.viewModel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_reminder.*
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderFragment : Fragment(), NavigationFragment, MenuItem.OnMenuItemClickListener,
    ReminderAdapter.ReminderActionListener {

    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var viewModel: ReminderViewModel
    private lateinit var addReminderViewModel: ReminderViewModel

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
        addReminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(reminderRecyclerView)

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

    private val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val reminder = reminderAdapter.getReminder(viewHolder.bindingAdapterPosition)
                viewModel.deleteReminder(reminder)
                requireView().snackbar("Reminder Deleted")
//                context?.let {
//                    it.toast("Reminder Deleted")
//                }
            }
        }

    override fun reminderStateChanged(reminder: Reminder, state: ReminderAdapter.ReminderState) {
        val uuid: UUID = UUID.fromString(reminder.workRequestId)
        val instance = WorkManager.getInstance(requireContext())

        when (state) {
            ReminderAdapter.ReminderState.ENABLE -> {
                val reminderRequest = OneTimeWorkRequest
                    .Builder(NotificationWorker::class.java)
                    .setInputData(reminder.createDate())
                    .setInitialDelay(reminder.millisToNotify(), TimeUnit.MILLISECONDS)
                    .build()
                instance.enqueue(reminderRequest)
                addReminderViewModel.update(reminder.copy(workRequestId = reminderRequest.id.toString()))
            }

            ReminderAdapter.ReminderState.DISABLE -> {
                instance.getWorkInfoByIdLiveData(uuid)
                    .observe(viewLifecycleOwner, Observer {
                        if (it != null && it.state == WorkInfo.State.ENQUEUED) {
                            WorkManager.getInstance(requireContext()).cancelWorkById(uuid)
                            addReminderViewModel.update(reminder.copy(workRequestId = null))
                        }
                    })
            }
        }
    }

    override fun onReminderClicked(reminder: Reminder) {
    }
}




