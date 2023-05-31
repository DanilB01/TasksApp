package ru.tsu.tasksapp.app.main.home

import ru.tsu.tasksapp.domain.common.TaskInfo

data class HomeItem(
    val title: String,
    val color: Int,
    val tasks: List<TaskInfo>
)
