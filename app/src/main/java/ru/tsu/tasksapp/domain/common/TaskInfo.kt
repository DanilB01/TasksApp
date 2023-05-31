package ru.tsu.tasksapp.domain.common

import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.TaskStatus

data class TaskInfo(
    val name: String,
    val date: String,
    val status: TaskStatus,
    val task: Task
)
