package ru.tsu.tasksapp.domain.task.single

import ru.tsu.tasksapp.domain.task.TaskStatus

data class SingleTask(
    val id: Int? = null,
    val dateTimestamp: Long? = null,
    val name: String? = null,
    val time: String? = null,
    val date: String? = null,
    val notificationTime: String? = null,
    val status: TaskStatus = TaskStatus.ACTIVE
)
