package ru.tsu.tasksapp.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tsu.tasksapp.data.credential.CredentialDao
import ru.tsu.tasksapp.data.credential.CredentialEntity
import ru.tsu.tasksapp.data.session.SessionDao
import ru.tsu.tasksapp.data.session.SessionEntity
import ru.tsu.tasksapp.data.task.regular.RegularTaskDao
import ru.tsu.tasksapp.data.task.regular.RegularTaskEntity
import ru.tsu.tasksapp.data.task.single.SingleTaskDao
import ru.tsu.tasksapp.data.task.single.SingleTaskEntity

@Database(
    entities = [
        CredentialEntity::class,
        SessionEntity::class,
        SingleTaskEntity::class,
        RegularTaskEntity::class
   ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCredentialDao(): CredentialDao
    abstract fun getSessionDao(): SessionDao
    abstract fun getSingleTaskDao(): SingleTaskDao
    abstract fun getRegularTaskDao(): RegularTaskDao
}