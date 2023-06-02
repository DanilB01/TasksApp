package ru.tsu.tasksapp.domain.task.regular

import ru.tsu.tasksapp.app.common.DateTimeUtils
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
                currentTaskDoneTimestamp = it.currentTaskDoneTimestamp.toLongOrNull(),
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
                creationTimestamp = task.creationTimestamp.toString(),
                currentTaskDoneTimestamp = task.currentTaskDoneTimestamp.toString()
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
                creationTimestamp = task.creationTimestamp.toString(),
                currentTaskDoneTimestamp = task.currentTaskDoneTimestamp.toString()
            )
        )
    }

    suspend fun markTaskDone(task: RegularTask) {
        Database.getRegularTaskDao().markTaskDone(
            id = task.id,
            status = TaskStatus.DONE.name
        )
        Database.getRegularTaskDao().setCurrentDoneDate(
            id = task.id,
            timestamp = getNextTimestamp(task).toString()
        )
    }

    suspend fun setCurrentTaskDoneTimestamp(task: RegularTask) {
        Database.getRegularTaskDao().setCurrentDoneDate(
            task.id, task.currentTaskDoneTimestamp.toString()
        )
    }

    suspend fun getTaskById(id: Int): RegularTask? {
        val entity = Database.getRegularTaskDao().getRegularTaskById(id) ?: return null
        return RegularTask(
            id = entity.id,
            creationTimestamp = entity.creationTimestamp.toLong(),
            currentTaskDoneTimestamp = entity.currentTaskDoneTimestamp.toLongOrNull(),
            name = entity.name,
            time = entity.time,
            periodValue = entity.periodValue,
            periodVariant = TaskPeriod.valueOf(entity.periodVariant),
            regularity = entity.regularity,
            notificationTime = entity.notificationTime,
            status = TaskStatus.valueOf(entity.status)
        )
    }

    suspend fun deleteTaskById(id: Int) {
        Database.getRegularTaskDao().deleteRegularTasksById(id)
    }

    private fun getNextTimestamp(task: RegularTask) = when(task.periodVariant) {
        TaskPeriod.DAY -> DateTimeUtils.getNextDayTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.WEEK -> DateTimeUtils.getNextWeekTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.MONTH -> DateTimeUtils.getNextMonthTimestamp(task.creationTimestamp!!, task.periodValue!!)
        null -> null
    }

}