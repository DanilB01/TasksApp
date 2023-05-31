package ru.tsu.tasksapp.app.task.regular.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class AddRegularTaskViewModel: ViewModel() {

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
}