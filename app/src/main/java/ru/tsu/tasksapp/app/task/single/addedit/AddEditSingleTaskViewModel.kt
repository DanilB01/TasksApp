package ru.tsu.tasksapp.app.task.single.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository
import java.text.DateFormat

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

    fun setDate(dateTimestamp: Long) {
        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        _currentTask.value = _currentTask.value?.copy(
            dateTimestamp = dateTimestamp,
            date = dateFormatter.format(dateTimestamp)
        )
    }

    fun updateTaskName(name: String) {
        _currentTask.value = _currentTask.value?.copy(name = name)
    }

    fun saveSingleTask() {
        viewModelScope.launch {
            val task = _currentTask.value ?: return@launch
            if(isEditingTask) {
                singleTaskRepository.updateSingleTask(task)
            } else {
                singleTaskRepository.addSingleTask(task)
            }
        }
    }
}