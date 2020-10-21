package com.aakashbista.note.appManager

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aakashbista.note.R
import com.aakashbista.note.ui.MainActivity


class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("TITLE_KEY")
        val description = inputData.getString("DESCRIPTION_KEY")
        sendNotification(title!!, description!!)
        return Result.success()
    }

    private fun sendNotification(title: String, description: String) {
        val notificationCompat = NotificationManagerCompat.from(applicationContext)

        var builder =
            NotificationCompat.Builder(applicationContext, AppNotificationManager.CHANNEL_ID)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_note_black)
                .setContentIntent(getMainActivityPendingIntent())

        notificationCompat.notify(1, builder.build())

    }


    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        applicationContext,
        0,
        Intent(applicationContext, MainActivity::class.java).also {
            it.action = applicationContext.getString(R.string.ACTION_SHOW_REMINDER_FRAGMENT)
        },
        FLAG_UPDATE_CURRENT
    )
}