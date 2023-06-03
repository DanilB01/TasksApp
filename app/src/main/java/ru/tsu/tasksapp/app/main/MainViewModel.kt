package ru.tsu.tasksapp.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository

class MainViewModel: ViewModel() {
    private val regularTaskRepository = RegularTaskRepository()

    private val _tasksToSetNotification = MutableLiveData<List<RegularTask>>()
    val tasksToSetNotification: LiveData<List<RegularTask>> = _tasksToSetNotification

    fun updateTasksToSetNotifications() {
        viewModelScope.launch {
            val allTasks = regularTaskRepository.getTasks()
            val activeForTodayTasks = allTasks.filter {
                it.currentTaskDoneTimestamp == null ||
                        checkIfRegularTaskActiveForToday(it)
            }
            _tasksToSetNotification.value = activeForTodayTasks.filter {
                it.lastSettingNotificationTimestamp == null ||
                        System.currentTimeMillis() > DateTimeUtils.atEndOfDay(it.lastSettingNotificationTimestamp)
            }
        }
    }

    fun setLastNotificationDate() {
        viewModelScope.launch {
            _tasksToSetNotification.value?.forEach {
                val currentTask = it.copy(
                        lastSettingNotificationTimestamp = System.currentTimeMillis()
                )
                regularTaskRepository.setLastSettingNotificationTimestamp(currentTask)
            }
        }
    }

    private fun checkIfRegularTaskActiveForToday(task: RegularTask) =
        task.status == TaskStatus.ACTIVE &&
        System.currentTimeMillis() > DateTimeUtils.atEndOfDay(task.currentTaskDoneTimestamp!!)
}