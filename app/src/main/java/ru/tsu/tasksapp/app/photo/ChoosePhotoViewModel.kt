package ru.tsu.tasksapp.app.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tsu.tasksapp.R

class ChoosePhotoViewModel: ViewModel() {

    private val _photos = MutableLiveData<List<PhotoItem>>()
    val photos: LiveData<List<PhotoItem>> = _photos

    init {
        getPhotos()
    }

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