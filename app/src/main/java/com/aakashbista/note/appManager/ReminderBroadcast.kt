package com.aakashbista.note.appManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aakashbista.note.R

class ReminderBroadcast() : BroadcastReceiver() {
    private lateinit var notificationCompat: NotificationManagerCompat
    override fun onReceive(context: Context?, intent: Intent?) {
        notificationCompat = NotificationManagerCompat.from(context!!)
        var builder = context?.let {
            NotificationCompat.Builder(it, AppNotificationManager.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon_round)
                .setContentTitle("Reminder from Notes")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build()
        }
        notificationCompat.notify(9, builder)
    }

}