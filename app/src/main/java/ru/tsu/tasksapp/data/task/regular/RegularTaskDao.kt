package ru.tsu.tasksapp.data.task.regular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RegularTaskDao {
    @Query("SELECT * FROM regular_tasks")
    suspend fun getRegularTasks(): List<RegularTaskEntity>?

    @Insert
    suspend fun addRegularTask(task: RegularTaskEntity)

    @Update
    suspend fun updateRegularTask(task: RegularTaskEntity)
}