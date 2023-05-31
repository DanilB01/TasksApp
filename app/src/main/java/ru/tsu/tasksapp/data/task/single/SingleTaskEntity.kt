package ru.tsu.tasksapp.data.task.single

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "single_tasks")
data class SingleTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String,
    val date: String,
    val notificationTime: String,
    val status: String,
    val dateTimestamp: String
)
