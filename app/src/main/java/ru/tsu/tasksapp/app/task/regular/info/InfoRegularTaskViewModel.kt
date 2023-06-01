package ru.tsu.tasksapp.app.task.regular.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.domain.photo.PhotoItem
import ru.tsu.tasksapp.app.photo.TaskValues
import ru.tsu.tasksapp.domain.photo.PhotoRepository
import ru.tsu.tasksapp.domain.session.SessionRepository
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.regular.RegularTaskRepository

class InfoRegularTaskViewModel: ViewModel() {
    private val regularTaskRepository = RegularTaskRepository()
    private val sessionRepository = SessionRepository()
    private val photoRepository = PhotoRepository()

    private val _currentTask = MutableLiveData<RegularTask>()
    val currentTask: LiveData<RegularTask> = _currentTask

    private val _isTaskActiveForToday = MutableLiveData<Boolean>()
    val isTaskActiveForToday: LiveData<Boolean> = _isTaskActiveForToday

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
            val task = regularTaskRepository.getTaskById(taskId) ?: return@launch
            _currentTask.value = task

            _isTaskActiveForToday.value = checkIfTaskActiveForToday(task) &&
                    task.status == TaskStatus.ACTIVE

            currentUserEmail = sessionRepository.getEmailFromSession()
            updatePhotos()
        }
    }

    fun setTaskDone() {
        viewModelScope.launch {
            _currentTask.value?.let { regularTaskRepository.markTaskDone(it) }
            _currentTask.value = _currentTask.value?.copy(status = TaskStatus.DONE)
            _isTaskActiveForToday.value = false
            TaskValues.setValues(
                currentTaskId = _currentTask.value?.id!!,
                isForSingleTask = false
            )
            _isShowAddPhotoDialog.value = currentUserEmail != null
        }
    }

    fun setTaskDoneForToday() {
        viewModelScope.launch {
            _currentTask.value?.let {
                regularTaskRepository.setCurrentTaskDoneTimestamp(
                    it.copy(currentTaskDoneTimestamp = System.currentTimeMillis())
                )
                _currentTask.value = it.copy(currentTaskDoneTimestamp = System.currentTimeMillis())
                _isTaskActiveForToday.value = false
                TaskValues.setValues(
                    currentTaskId = _currentTask.value?.id!!,
                    isForSingleTask = false
                )
                _isShowAddPhotoDialog.value = currentUserEmail != null
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            _currentTask.value?.id?.let { regularTaskRepository.deleteTaskById(it) }
        }
    }

    private fun checkIfTaskActiveForToday(task: RegularTask) = task.currentTaskDoneTimestamp == null ||
            System.currentTimeMillis() > DateTimeUtils.atEndOfDay(task.currentTaskDoneTimestamp)

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