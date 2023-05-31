package ru.tsu.tasksapp.app.task.single.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository

class AddEditSingleTaskViewModel: ViewModel() {
    private val singleTaskRepository = SingleTaskRepository()

    private val _currentTask = MutableLiveData<SingleTask>()
    val currentTask: LiveData<SingleTask> = _currentTask

    private var isNotificationTimeSelecting: Boolean? = null
    private var isEditingTask: Boolean = false

    init {
        _currentTask.value = SingleTask()
    }

    fun initCurrentTask(task: SingleTask) {
        _currentTask.value = task
        isEditingTask = true
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

    fun setDate(date: String) {
        _currentTask.value = _currentTask.value?.copy(date = date)
    }

    fun updateTaskName(name: String) {
        _currentTask.value = _currentTask.value?.copy(name = name)
    }

    fun saveSingleTask() {
        viewModelScope.launch {
            var task = _currentTask.value ?: return@launch
            if(isEditingTask) {
                singleTaskRepository.updateSingleTask(task)
            } else {
                task = task.copy(creationTimestamp = System.currentTimeMillis())
                singleTaskRepository.addSingleTask(task)
            }
        }
    }
}