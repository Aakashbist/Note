package com.aakashbista.note.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.aakashbista.note.R
import com.aakashbista.note.db.Reminder
import kotlinx.android.synthetic.main.note_item.view.reminderDescription
import kotlinx.android.synthetic.main.note_item.view.reminderTitle
import kotlinx.android.synthetic.main.reminder_item.view.*
import java.text.DateFormat

class ReminderAdapter(private val onChange: (reminder: Reminder, isChecked: Boolean) -> Unit) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reminders = emptyList<Reminder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        )
    }

    override fun getItemCount() = reminders.count()

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.setReminderView(
            reminders[position],
            CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
                onChange(reminders[position], isChecked)
            }
        )
    }

    fun setReminder(reminders: List<Reminder>) {
        this.reminders = reminders
        notifyDataSetChanged()
    }

    class ReminderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setReminderView(
            reminder: Reminder,
            onCheckedChangeListener: CompoundButton.OnCheckedChangeListener
        ) {
            view.reminderTitle.text = reminder.title
            view.reminderDescription.text = reminder.description
            view.dateTime.text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(reminder.dateTime.time)
            view.action_switch.setOnCheckedChangeListener(onCheckedChangeListener)
        }

    }
}