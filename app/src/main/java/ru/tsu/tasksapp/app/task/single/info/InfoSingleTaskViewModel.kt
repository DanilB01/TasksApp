package ru.tsu.tasksapp.app.task.single.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.domain.photo.PhotoItem
import ru.tsu.tasksapp.app.photo.TaskValues
import ru.tsu.tasksapp.domain.photo.PhotoRepository
import ru.tsu.tasksapp.domain.session.SessionRepository
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository

class InfoSingleTaskViewModel: ViewModel() {
    private val singleTaskRepository = SingleTaskRepository()
    private val sessionRepository = SessionRepository()
    private val photoRepository = PhotoRepository()

    private val _currentTask = MutableLiveData<SingleTask>()
    val currentTask: LiveData<SingleTask> = _currentTask

    private val _isOverdue = MutableLiveData<Boolean>()
    val isOverdue: LiveData<Boolean> = _isOverdue

    private val _isPhotosVisible = MutableLiveData<Boolean>()
    val isPhotosVisible: LiveData<Boolean> = _isPhotosVisible

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    private val _isShowAddPhotoDialog = MutableLiveData<Boolean>()
    val isShowAddPhotoDialog: LiveData<Boolean> = _isShowAddPhotoDialog

    private var currentUserEmail: String? = null
        set(value) {
            _isPhotosVisible.value = value != null
            field = value
        }

    fun reloadPhotos() {
        viewModelScope.launch {
            updatePhotos()
        }
    }

    fun loadTaskInfo(taskId: Int) {
        viewModelScope.launch {
            val task = singleTaskRepository.getTaskById(taskId) ?: return@launch
            _currentTask.value = task
            _isOverdue.value = isOverdue(task)
            currentUserEmail = sessionRepository.getEmailFromSession()
            updatePhotos()
        }
    }

    fun setTaskDone() {
        viewModelScope.launch {
            _currentTask.value?.let { singleTaskRepository.markTaskDone(it) }
            _currentTask.value = _currentTask.value?.copy(status = TaskStatus.DONE)
            TaskValues.setValues(
                currentTaskId = _currentTask.value?.id!!,
                isForSingleTask = true
            )
            _isShowAddPhotoDialog.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            _currentTask.value?.id?.let { singleTaskRepository.deleteTaskById(it) }
        }
    }

    private fun isOverdue(task: SingleTask) =
        System.currentTimeMillis() > DateTimeUtils.atEndOfDay(task.dateTimestamp!!) &&
                task.status == TaskStatus.ACTIVE

    private suspend fun updatePhotos() {
        val taskId = TaskValues.getCurrentTaskId
        val isForSingleTask = TaskValues.getIsForSingleTask
        if (taskId == null || isForSingleTask == null) return
        _photos.value = photoRepository.getPhotosByTask(
            taskId = taskId,
            isForSingleTask = isForSingleTask
        )
    }
}