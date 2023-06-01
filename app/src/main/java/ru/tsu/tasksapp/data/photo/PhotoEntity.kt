package ru.tsu.tasksapp.data.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskId: Int,
    val isForSingleTask: Boolean,
    val uri: String,
    val name: String,
    val size: String
)
