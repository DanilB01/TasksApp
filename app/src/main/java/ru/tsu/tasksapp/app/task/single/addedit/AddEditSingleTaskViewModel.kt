package ru.tsu.tasksapp.app.task.single.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tsu.tasksapp.domain.task.single.SingleTask

class AddEditSingleTaskViewModel: ViewModel() {
    private val _currentTask = MutableLiveData<SingleTask>()
    val currentTask: LiveData<SingleTask> = _currentTask

    private var isNotificationTimeSelecting: Boolean? = null

    init {
        _currentTask.value = SingleTask()
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
}