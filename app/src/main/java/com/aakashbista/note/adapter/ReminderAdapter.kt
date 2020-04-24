package com.aakashbista.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Reminder
import com.aakashbista.note.extension.formatedDate
import kotlinx.android.synthetic.main.add_reminder_fragment.*
import kotlinx.android.synthetic.main.note_item.view.reminderDescription
import kotlinx.android.synthetic.main.note_item.view.reminderTitle
import kotlinx.android.synthetic.main.reminder_item.view.*
import java.text.DateFormat
import java.time.format.DateTimeFormatter

class ReminderAdapter(
  private val reminderActionListner: ReminderActionListener
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reminders = emptyList<Reminder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        )
    }

    override fun getItemCount() = reminders.count()

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.setReminder(
            reminders[position],
            reminderActionListner
        )
    }

    fun setReminder(reminders: List<Reminder>) {
        this.reminders = reminders
        notifyDataSetChanged()
    }

    class ReminderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setReminder(
            reminder: Reminder,
            reminderActionListener: ReminderActionListener
        ) {
            view.reminderTitle.text = reminder.title
            view.reminderDescription.text = reminder.description
            view.action_switch.isChecked = reminder.isEnabled
            view.dateTime.text= reminder.dateTime.formatedDate()
            view.setOnClickListener {
                reminderActionListener.reminderOpen(reminder)
            }
            view.action_switch.setOnCheckedChangeListener{_, isChecked: Boolean ->
                reminderActionListener.reminderStateChanged(reminder, ReminderState.from(isChecked))
            }
        }

    }
    interface ReminderActionListener {
        fun reminderOpen(reminder: Reminder)
        fun reminderStateChanged(reminder: Reminder,state:ReminderState)
    }

    enum class ReminderState{
        ENABLE,
        DISABLE;

        companion object {
            fun from(value : Boolean): ReminderState = if (value) ENABLE else DISABLE
        }
    }
}