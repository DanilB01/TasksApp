package ru.tsu.tasksapp.data.common

import android.content.Context
import androidx.room.Room

object Database {
    private lateinit var database: AppDatabase
    fun initDatabase(context: Context) {
        if (::database.isInitialized) return
        database = build(context)
    }

    fun getCredentialDao() = database.getCredentialDao()
    fun getSessionDao() = database.getSessionDao()
    fun getSingleTaskDao() = database.getSingleTaskDao()
    fun getRegularTaskDao() = database.getRegularTaskDao()
    fun getPhotoDao() = database.getPhotoDao()

    private fun build(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "tasks_app_database"
    ).build()
}