package ru.tsu.tasksapp.domain.task.single

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.task.single.SingleTaskEntity
import ru.tsu.tasksapp.domain.task.TaskStatus

class SingleTaskRepository {

    suspend fun getTasks(): List<SingleTask> {
        val entities = Database.getSingleTaskDao().getSingleTasks() ?: return emptyList()
        return entities.map {
            SingleTask(
                id = it.id,
                dateTimestamp = it.dateTimestamp.toLong(),
                name = it.name,
                time = it.time,
                date = it.date,
                notificationTime = it.notificationTime,
                status = TaskStatus.valueOf(it.status)
            )
        }
    }
    suspend fun addSingleTask(task: SingleTask) {
        if (task.name == null ||
            task.time == null ||
            task.date == null ||
            task.notificationTime == null ||
            task.dateTimestamp == null
        ) {
            return
        }
        Database.getSingleTaskDao().addSingleTask(
            SingleTaskEntity(
                name = task.name,
                time = task.time,
                date = task.date,
                notificationTime = task.notificationTime,
                status = task.status.name,
                dateTimestamp = task.dateTimestamp.toString()
            )
        )
    }

    suspend fun updateSingleTask(task: SingleTask) {
        if (task.name == null ||
            task.time == null ||
            task.date == null ||
            task.notificationTime == null ||
            task.dateTimestamp == null
        ) {
            return
        }
        Database.getSingleTaskDao().updateSingleTask(
            SingleTaskEntity(
                id = task.id,
                name = task.name,
                time = task.time,
                date = task.date,
                notificationTime = task.notificationTime,
                status = task.status.name,
                dateTimestamp = task.dateTimestamp.toString()
            )
        )
    }

    suspend fun markTaskDone(task: SingleTask) {
        Database.getSingleTaskDao().markTaskDone(
            id = task.id,
            status = TaskStatus.DONE.name
        )
    }
}