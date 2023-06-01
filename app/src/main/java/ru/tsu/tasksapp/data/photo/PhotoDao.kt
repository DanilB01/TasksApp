package ru.tsu.tasksapp.data.photo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos WHERE taskId = :taskId AND isForSingleTask = :isForSingleTask")
    suspend fun getPhotosByTaskId(taskId: Int, isForSingleTask: Boolean): List<PhotoEntity>?

    @Insert
    suspend fun savePhoto(photo: PhotoEntity)

    @Query("DELETE FROM photos WHERE id = :id")
    suspend fun deletePhotoById(id: Int)
}