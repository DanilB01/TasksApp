package ru.tsu.tasksapp.data.task.regular

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regular_tasks")
data class RegularTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: String,
    val periodValue: Int,
    val periodVariant: String,
    val regularity: String,
    val notificationTime: String,
    val status: String,
    val creationTimestamp: String,
    val currentTaskDoneTimestamp: String,
)
