package ru.tsu.tasksapp.domain.task.single

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.task.single.SingleTaskEntity

class SingleTaskRepository {
    suspend fun addSingleTask(task: SingleTask) {
        if (task.name == null ||
            task.time == null ||
            task.date == null ||
            task.notificationTime == null
        ) {
            return
        }
        Database.getSingleTaskDao().addSingleTask(
            SingleTaskEntity(
                name = task.name,
                time = task.time,
                date = task.date,
                notificationTime = task.notificationTime,
                status = task.status.name
            )
        )
    }

    suspend fun updateSingleTask(task: SingleTask) {
        if (task.name == null ||
            task.time == null ||
            task.date == null ||
            task.notificationTime == null
        ) {
            return
        }
        Database.getSingleTaskDao().updateSingleTask(
            SingleTaskEntity(
                name = task.name,
                time = task.time,
                date = task.date,
                notificationTime = task.notificationTime,
                status = task.status.name
            )
        )
    }
}