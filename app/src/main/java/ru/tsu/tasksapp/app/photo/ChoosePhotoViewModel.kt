package ru.tsu.tasksapp.app.photo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.photo.PhotoItem
import ru.tsu.tasksapp.domain.photo.PhotoRepository

class ChoosePhotoViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    init {
        viewModelScope.launch {
            updatePhotos()
        }
    }

    fun savePhoto(uri: Uri) {
        viewModelScope.launch {
            photoRepository.savePhoto(
                uri = uri.toString(),
                name = "image.jpg",
                size = "1,5 МБ"
            )
            updatePhotos()
        }
    }

    fun removePhoto(photo: PhotoItem) {
        viewModelScope.launch {
            photoRepository.deletePhotoById(photo.id)
            updatePhotos()
        }
    }

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