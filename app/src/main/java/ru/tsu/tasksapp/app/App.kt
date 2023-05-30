package ru.tsu.tasksapp.app

import android.app.Application
import ru.tsu.tasksapp.data.common.Database

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Database.initDatabase(applicationContext)
    }
}