package ru.tsu.tasksapp.domain.task.regular

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.task.regular.RegularTaskEntity

class RegularTaskRepository {
    suspend fun addTask(task: RegularTask) {
        if (task.name == null ||
            task.time == null ||
            task.regularity == null ||
            task.notificationTime == null ||
            task.creationTimestamp == null ||
            task.periodValue == null ||
            task.periodVariant == null
        ) {
            return
        }
        Database.getRegularTaskDao().addRegularTask(
            RegularTaskEntity(
                name = task.name,
                time = task.time,
                periodValue = task.periodValue,
                periodVariant = task.periodVariant.name,
                regularity = task.regularity,
                notificationTime = task.notificationTime,
                status = task.status.name,
                creationTimestamp = task.creationTimestamp.toString()
            )
        )
    }
}