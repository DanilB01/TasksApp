package ru.tsu.tasksapp.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tsu.tasksapp.data.credential.CredentialDao
import ru.tsu.tasksapp.data.credential.CredentialEntity
import ru.tsu.tasksapp.data.session.SessionDao
import ru.tsu.tasksapp.data.session.SessionEntity

@Database(
    entities = [
        CredentialEntity::class,
        SessionEntity::class
   ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCredentialDao(): CredentialDao
    abstract fun getSessionDao(): SessionDao
}