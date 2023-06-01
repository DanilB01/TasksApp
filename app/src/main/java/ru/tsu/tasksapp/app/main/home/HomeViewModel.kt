package ru.tsu.tasksapp.app.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.photo.TaskValues
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.session.SessionRepository
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
    private val sessionRepository = SessionRepository()

    private val _homeItems = MutableLiveData<List<HomeItem>>()
    val homeItems: LiveData<List<HomeItem>> = _homeItems

    private val _isShowAddPhotoDialog = MutableLiveData<Boolean>()
    val isShowAddPhotoDialog: LiveData<Boolean> = _isShowAddPhotoDialog

    fun resetShowAddPhotoDialogFlag() {
        _isShowAddPhotoDialog.value = false
    }

    fun markTaskDone(task: Task) {
        viewModelScope.launch {
            when (task) {
                is SingleTask -> {
                    singleTaskRepository.markTaskDone(task)
                    TaskValues.setValues(
                        currentTaskId = task.id,
                        isForSingleTask = true
                    )
                }
                is RegularTask -> {
                    regularTaskRepository.setCurrentTaskDoneTimestamp(
                        task.copy(currentTaskDoneTimestamp = System.currentTimeMillis())
                    )
                    TaskValues.setValues(
                        currentTaskId = task.id,
                        isForSingleTask = false
                    )
                }
            }
            _isShowAddPhotoDialog.value = !sessionRepository.getEmailFromSession().isNullOrEmpty()
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
                    tasks = getOverdueSingleTasks(singleTasks)
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

    private fun getTodayRegularTasks(tasks: List<RegularTask>): List<TaskInfo> =
        tasks
            .filter {
                /*System.currentTimeMillis() > DateTimeUtils.atStartOfDay(it.creationTimestamp!!) &&
                        System.currentTimeMillis() < DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!) &&
                        it.status == TaskStatus.ACTIVE*/
                it.currentTaskDoneTimestamp == null ||
                System.currentTimeMillis() > DateTimeUtils.atEndOfDay(it.currentTaskDoneTimestamp)
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
                /*System.currentTimeMillis() > DateTimeUtils.atStartOfDay(getNextTimestamp(it)!!) &&
                        System.currentTimeMillis() < DateTimeUtils.atEndOfDay(getNextTimestamp(it)!!) &&
                        it.status == TaskStatus.DONE*/
                 checkIsRegularTaskDone(it) || it.status == TaskStatus.DONE
            }
            .map {
                TaskInfo(
                    name = it.name!!,
                    date = DateTimeUtils.getDateString(getNextTimestamp(it)!!),
                    status = TaskStatus.DONE,
                    task = it
                )
            }

    private fun getNextTimestamp(task: RegularTask) = when(task.periodVariant) {
        TaskPeriod.DAY -> DateTimeUtils.getNextDayTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.WEEK -> DateTimeUtils.getNextWeekTimestamp(task.creationTimestamp!!, task.periodValue!!)
        TaskPeriod.MONTH -> DateTimeUtils.getNextMonthTimestamp(task.creationTimestamp!!, task.periodValue!!)
        null -> null
    }

    private fun checkIsRegularTaskDone(task: RegularTask) = task.currentTaskDoneTimestamp != null &&
            System.currentTimeMillis() < DateTimeUtils.atEndOfDay(task.currentTaskDoneTimestamp)
}