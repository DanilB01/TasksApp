package ru.tsu.tasksapp.app.task.single.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.photo.PhotoItem
import ru.tsu.tasksapp.domain.session.SessionRepository
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.single.SingleTask
import ru.tsu.tasksapp.domain.task.single.SingleTaskRepository

class InfoSingleTaskViewModel: ViewModel() {
    private val singleTaskRepository = SingleTaskRepository()
    private val sessionRepository = SessionRepository()

    private val _currentTask = MutableLiveData<SingleTask>()
    val currentTask: LiveData<SingleTask> = _currentTask

    private val _isOverdue = MutableLiveData<Boolean>()
    val isOverdue: LiveData<Boolean> = _isOverdue

    private val _isPhotosVisible = MutableLiveData<Boolean>()
    val isPhotosVisible: LiveData<Boolean> = _isPhotosVisible

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    private var currentUserEmail: String? = null
        set(value) {
            _isPhotosVisible.value = value != null
            field = value
        }

    fun loadTaskInfo(taskId: Int) {
        viewModelScope.launch {
            val task = singleTaskRepository.getTaskById(taskId) ?: return@launch
            _currentTask.value = task
            _isOverdue.value = isOverdue(task)
            currentUserEmail = sessionRepository.getEmailFromSession()
            getPhotos()
        }
    }

    fun setTaskDone() {
        viewModelScope.launch {
            _currentTask.value?.let { singleTaskRepository.markTaskDone(it) }
            _currentTask.value = _currentTask.value?.copy(status = TaskStatus.DONE)
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

    private fun getPhotos() {
        // TODO: add getting photo from db
        val res = mutableListOf<PhotoItem>()
        repeat(5) {
            res.add(
                PhotoItem(
                    drawable = R.drawable.pic_status_active,
                    name = "image.png",
                    size = "1.5 МБ"
                )
            )
        }
        _photos.value = res
    }
}