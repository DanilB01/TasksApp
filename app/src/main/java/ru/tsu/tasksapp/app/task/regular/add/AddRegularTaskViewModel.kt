package ru.tsu.tasksapp.app.task.regular.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository

class AddRegularTaskViewModel: ViewModel() {
    private val regularTaskRepository = RegularTaskRepository()

    private val _currentTask = MutableLiveData<RegularTask>()
    val currentTask: LiveData<RegularTask> = _currentTask

    private var isNotificationTimeSelecting: Boolean? = null

    init {
        _currentTask.value = RegularTask()
    }

    fun setTaskName(name: String) {
        _currentTask.value = _currentTask.value?.copy(name = name)
    }

    fun setIsNotificationTimeSelecting(flag: Boolean) {
        isNotificationTimeSelecting = flag
    }

    fun setTime(time: String) {
        isNotificationTimeSelecting?.let {
            if (it) {
                _currentTask.value = _currentTask.value?.copy(notificationTime = time)
            } else {
                _currentTask.value = _currentTask.value?.copy(time = time)
            }
        }
        isNotificationTimeSelecting = null
    }

    fun setPeriod(period: String) {
        _currentTask.value = _currentTask.value?.copy(regularity = period)
    }

    fun saveTask() {
        viewModelScope.launch {
            var task = _currentTask.value ?: return@launch
            task = task.copy(creationTimestamp = System.currentTimeMillis())
            regularTaskRepository.addTask(task)
        }
    }
}