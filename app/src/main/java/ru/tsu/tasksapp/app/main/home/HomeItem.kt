package ru.tsu.tasksapp.app.main.home

import androidx.annotation.ColorRes
import ru.tsu.tasksapp.domain.common.TaskInfo

data class HomeItem(
    val title: String,
    @ColorRes
    val color: Int,
    val tasks: List<TaskInfo>
)
