package ru.tsu.tasksapp.domain.task.single

data class SingleTask(
    val name: String? = null,
    val time: String? = null,
    val date: String? = null,
    val notificationTime: String? = null
)
