package ru.tsu.tasksapp.data.session

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    val email: String
)
