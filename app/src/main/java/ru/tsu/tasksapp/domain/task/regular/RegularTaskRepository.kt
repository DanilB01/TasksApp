package ru.tsu.tasksapp.domain.task.regular

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.task.regular.RegularTaskEntity
import ru.tsu.tasksapp.domain.task.TaskStatus

class RegularTaskRepository {

    suspend fun getTasks(): List<RegularTask> {
        val entities = Database.getRegularTaskDao().getRegularTasks() ?: return emptyList()
        return entities.map {
            RegularTask(
                id = it.id,
                creationTimestamp = it.creationTimestamp.toLong(),
                name = it.name,
                time = it.time,
                periodValue = it.periodValue,
                periodVariant = TaskPeriod.valueOf(it.periodVariant),
                regularity = it.regularity,
                notificationTime = it.notificationTime,
                status = TaskStatus.valueOf(it.status)
            )
        }
    }
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

    suspend fun updateTask(task: RegularTask) {
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
        Database.getRegularTaskDao().updateRegularTask(
            RegularTaskEntity(
                id = task.id,
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

    suspend fun markTaskDone(task: RegularTask) {
        Database.getRegularTaskDao().markTaskDone(
            id = task.id,
            status = TaskStatus.DONE.name
        )
    }
}