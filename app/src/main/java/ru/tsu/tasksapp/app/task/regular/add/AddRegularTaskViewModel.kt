package ru.tsu.tasksapp.app.task.regular.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class AddRegularTaskViewModel: ViewModel() {

    private val _currentTask = MutableLiveData<RegularTask>()
    val currentTask: LiveData<RegularTask> = _currentTask

    init {
        _currentTask.value = RegularTask()
    }

    fun setTaskName(name: String) {
        _currentTask.value = _currentTask.value?.copy(name = name)
    }

    fun setTime(time: String) {
        _currentTask.value = _currentTask.value?.copy(time = time)
    }
}