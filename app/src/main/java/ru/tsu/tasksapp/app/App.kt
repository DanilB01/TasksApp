package ru.tsu.tasksapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import ru.tsu.tasksapp.app.notification.channelId
import ru.tsu.tasksapp.data.common.Database

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Database.initDatabase(applicationContext)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Task channel"
        val description = "Notification channel for tasks"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}