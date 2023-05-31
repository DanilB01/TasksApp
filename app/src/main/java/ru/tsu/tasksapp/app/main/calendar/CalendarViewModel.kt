package ru.tsu.tasksapp.app.main.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.main.home.HomeItem
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository
import ru.tsu.tasksapp.domain.task.regular.TaskPeriod
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository

class CalendarViewModel: ViewModel() {
    private val singleTaskRepository = SingleTaskRepository()
    private val regularTaskRepository = RegularTaskRepository()

    private val _tasks = MutableLiveData<List<TaskInfo>>()
    val tasks: LiveData<List<TaskInfo>> = _tasks

    fun updateTasks(timestamp: Long) {
        viewModelScope.launch {
            val singleTasks = singleTaskRepository.getTasks()
            val regularTasks = regularTaskRepository.getTasks()

            val res1 = getTodaySingleTasks(singleTasks, timestamp)
            val res2 = getTodayRegularTasks(regularTasks, timestamp)
            _tasks.value = res1 + res2
        }
    }

    private fun getTodaySingleTasks(tasks: List<SingleTask>, timestamp: Long): List<TaskInfo> =
        tasks
            .filter {
                timestamp > DateTimeUtils.atStartOfDay(it.dateTimestamp!!) &&
                timestamp < DateTimeUtils.atEndOfDay(it.dateTimestamp!!)
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = it.date!!,
                    status = it.status,
                    task = it
                )
            }

    private fun getTodayRegularTasks(tasks: List<RegularTask>, timestamp: Long): List<TaskInfo> =
        tasks
            .filter {
                timestamp > DateTimeUtils.atStartOfDay(getNextTimestamp(it)!!) &&
                timestamp < DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!)
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = DateTimeUtils.getDateString(getNextTimestamp(it)!!),
                    status = it.status,
                    task = it
                )
            }

    private fun getNextTimestamp(task: RegularTask) = when(task.periodVariant) {
        TaskPeriod.DAY -> DateTimeUtils.getNextDayTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.WEEK -> DateTimeUtils.getNextWeekTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.MONTH -> DateTimeUtils.getNextMonthTimestamp(task.creationTimestamp!!, task.periodValue!!)
        null -> null
    }
}