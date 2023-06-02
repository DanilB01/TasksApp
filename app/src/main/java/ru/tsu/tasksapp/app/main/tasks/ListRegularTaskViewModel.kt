package ru.tsu.tasksapp.app.main.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository

class ListRegularTaskViewModel: ViewModel() {
    private val regularTaskRepository = RegularTaskRepository()

    private val _tasks = MutableLiveData<List<TaskInfo>>()
    val tasks: LiveData<List<TaskInfo>> = _tasks

    private var startDateTimestamp: Long? = null
    private var endDateTimestamp: Long? = null

    fun updateTasks(startDateTimestamp: Long, endDateTimestamp: Long) {
        this.startDateTimestamp = startDateTimestamp
        this.endDateTimestamp = endDateTimestamp
        viewModelScope.launch {
            val allTasks = regularTaskRepository.getTasks()

            val tasksInPeriod = allTasks.filter {
                checkIfTaskCreatedInPeriod(it) ||
                checkIfTaskLastDoneInPeriod(it)
            }

            val res = mutableListOf<TaskInfo>()
            tasksInPeriod.forEach {
                res.addAll(getTaskItems(it))
            }
            _tasks.value = res
        }
    }

    private fun checkIfTaskCreatedInPeriod(task: RegularTask) =
        task.creationTimestamp!! < DateTimeUtils.atStartOfDay(startDateTimestamp!!) &&
        task.creationTimestamp!! > DateTimeUtils.atEndOfDay(endDateTimestamp!!)

    private fun checkIfTaskLastDoneInPeriod(task: RegularTask) =
        task.currentTaskDoneTimestamp!! < DateTimeUtils.atStartOfDay(startDateTimestamp!!) &&
        task.currentTaskDoneTimestamp!! > DateTimeUtils.atEndOfDay(endDateTimestamp!!)

    private fun getTaskItems(task: RegularTask): List<TaskInfo> {
        var currentStartTimestamp = startDateTimestamp ?: return emptyList()
        var currentEndTimestamp = endDateTimestamp ?: return emptyList()

        if (task.creationTimestamp!! > currentStartTimestamp) {
            currentStartTimestamp = task.creationTimestamp
        }

        if (task.currentTaskDoneTimestamp!! < currentEndTimestamp) {
            currentEndTimestamp = task.currentTaskDoneTimestamp
        }

        var currentTimestamp = currentStartTimestamp
        val res = mutableListOf<TaskInfo>()
        while (currentTimestamp < currentEndTimestamp) {
            res.add(
                TaskInfo(
                    name = task.name!!,
                    date = DateTimeUtils.getDateString(currentTimestamp),
                    status = TaskStatus.DONE,
                    task = task
                )
            )
            currentTimestamp = DateTimeUtils.getNextDayTimestamp(currentTimestamp, 1)
        }
        return res
    }
}