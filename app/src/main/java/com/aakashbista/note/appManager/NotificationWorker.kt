package com.aakashbista.note.appManager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aakashbista.note.R


class NotificationWorker (appContext: Context, workerParams: WorkerParameters)
: Worker(appContext, workerParams){

    override fun doWork(): Result {
        sendNotification("Note", "Reminder of notes")
        return Result.success()
    }

    fun sendNotification(title: String, description:String){
        val notificationCompat=NotificationManagerCompat.from(applicationContext)

        var builder =  NotificationCompat.Builder(applicationContext, AppNotificationManager.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon_background)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)


        notificationCompat.notify(1, builder.build())

    }
}