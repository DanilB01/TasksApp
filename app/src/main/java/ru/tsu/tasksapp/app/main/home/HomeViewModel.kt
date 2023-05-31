package ru.tsu.tasksapp.app.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository
import ru.tsu.tasksapp.domain.task.regular.TaskPeriod
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository

class HomeViewModel: ViewModel() {
    private val singleTaskRepository = SingleTaskRepository()
    private val regularTaskRepository = RegularTaskRepository()

    private val _homeItems = MutableLiveData<List<HomeItem>>()
    val homeItems: LiveData<List<HomeItem>> = _homeItems

    fun updateTask(task: Task) {
        viewModelScope.launch {
            when (task) {
                is SingleTask -> singleTaskRepository.markTaskDone(task)
                is RegularTask -> regularTaskRepository.markTaskDone(task)
            }
        }
        updateHomeItems()
    }

    fun updateHomeItems() {
        viewModelScope.launch {
            val singleTasks = singleTaskRepository.getTasks()
            val regularTasks = regularTaskRepository.getTasks()

            val result = mutableListOf<HomeItem>()
            result.add(
                HomeItem(
                    title = "Сегодня",
                    color = R.color.accent_yellow,
                    tasks = getTodaySingleTasks(singleTasks) + getTodayRegularTasks(regularTasks)
                )
            )
            result.add(
                HomeItem(
                    title = "Просрочено",
                    color = R.color.accent,
                    tasks = getOverdueSingleTasks(singleTasks) + getOverdueRegularTasks(regularTasks)
                )
            )
            result.add(
                HomeItem(
                    title = "Выполнено",
                    color = R.color.accent_green,
                    tasks = getDoneSingleTasks(singleTasks) + getDoneRegularTasks(regularTasks)
                )
            )
            _homeItems.value = result
        }
    }

    private fun getTodaySingleTasks(tasks: List<SingleTask>): List<TaskInfo> =
        tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atStartOfDay(it.dateTimestamp!!) &&
                System.currentTimeMillis() < DateTimeUtils.atEndOfDay(it.dateTimestamp!!) &&
                it.status == TaskStatus.ACTIVE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = it.date!!,
                    status = it.status,
                    task = it
                )
            }

    private fun getOverdueSingleTasks(tasks: List<SingleTask>): List<TaskInfo> =
        tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atEndOfDay(it.dateTimestamp!!) &&
                it.status == TaskStatus.ACTIVE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = it.date!!,
                    status = it.status,
                    task = it
                )
            }

    private fun getDoneSingleTasks(tasks: List<SingleTask>): List<TaskInfo> =
        tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atStartOfDay(it.dateTimestamp!!) &&
                System.currentTimeMillis() < DateTimeUtils.atEndOfDay(it.dateTimestamp!!) &&
                it.status == TaskStatus.DONE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = it.date!!,
                    status = it.status,
                    task = it
                )
            }

    private fun getTodayRegularTasks(tasks: List<RegularTask>): List<TaskInfo> {
        tasks.forEach {
            val current = System.currentTimeMillis()
            val timestamp = getNextTimestamp(it)
            val flag1 = DateTimeUtils.atStartOfDay(getNextTimestamp(it)!!)
            val flag2 = DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!)
        }
        return tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atStartOfDay(getNextTimestamp(it)!!) &&
                        System.currentTimeMillis() < DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!) &&
                        it.status == TaskStatus.ACTIVE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = DateTimeUtils.getDateString(getNextTimestamp(it)!!),
                    status = it.status,
                    task = it
                )
            }
    }

    private fun getOverdueRegularTasks(tasks: List<RegularTask>): List<TaskInfo> =
        tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!) &&
                        it.status == TaskStatus.ACTIVE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = DateTimeUtils.getDateString(getNextTimestamp(it)!!),
                    status = it.status,
                    task = it
                )
            }

    private fun getDoneRegularTasks(tasks: List<RegularTask>): List<TaskInfo> =
        tasks
            .filter {
                System.currentTimeMillis() > DateTimeUtils.atStartOfDay(getNextTimestamp(it)!!) &&
                        System.currentTimeMillis() < DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!) &&
                        it.status == TaskStatus.DONE
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