package ru.tsu.tasksapp.domain.task.single

data class SingleTask(
    val id: Int? = null,
    val name: String? = null,
    val time: String? = null,
    val date: String? = null,
    val notificationTime: String? = null,
    val status: SingleTaskStatus = SingleTaskStatus.ACTIVE
)
