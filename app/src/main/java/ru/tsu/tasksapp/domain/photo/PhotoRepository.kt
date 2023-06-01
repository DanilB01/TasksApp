package ru.tsu.tasksapp.domain.photo

import ru.tsu.tasksapp.app.photo.TaskValues
import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.photo.PhotoEntity

class PhotoRepository {
    suspend fun getPhotosByTask(taskId: Int, isForSingleTask: Boolean): List<PhotoItem> {
        val entities = Database.getPhotoDao().getPhotosByTaskId(taskId, isForSingleTask) ?: return emptyList()
        return entities.map {
            PhotoItem(
                id = it.id,
                uri = it.uri,
                name = it.name,
                size = it.size
            )
        }
    }

    suspend fun savePhoto(uri: String, name: String, size: String) {
        val taskId = TaskValues.getCurrentTaskId ?: return
        val isForSingleTask = TaskValues.getIsForSingleTask ?: return
        Database.getPhotoDao().savePhoto(
            PhotoEntity(
                taskId = taskId,
                isForSingleTask = isForSingleTask,
                uri = uri,
                name = name,
                size = size
            )
        )
    }

    suspend fun deletePhotoById(id: Int) {
        Database.getPhotoDao().deletePhotoById(id)
    }
}