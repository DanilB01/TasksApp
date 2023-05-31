package ru.tsu.tasksapp.domain.task.regular

import ru.tsu.tasksapp.domain.task.TaskStatus

data class RegularTask(
    val id: Int? = null,
    val creationTimestamp: Long? = null,
    val name: String? = null,
    val time: String? = null,
    val regularity: String? = null,
    val notificationTime: String? = null,
    val status: TaskStatus = TaskStatus.ACTIVE
)
