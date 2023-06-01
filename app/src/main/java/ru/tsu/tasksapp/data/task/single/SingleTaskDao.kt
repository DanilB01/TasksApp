package ru.tsu.tasksapp.data.task.single

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SingleTaskDao {
    @Query("SELECT * FROM single_tasks")
    suspend fun getSingleTasks(): List<SingleTaskEntity>?

    @Query("SELECT * FROM single_tasks WHERE id = :id")
    suspend fun getSingleTaskById(id: Int): SingleTaskEntity?

    @Insert
    suspend fun addSingleTask(task: SingleTaskEntity)

    @Update
    suspend fun updateSingleTask(task: SingleTaskEntity)

    @Query("UPDATE single_tasks SET status = :status WHERE id = :id")
    suspend fun markTaskDone(id: Int, status: String)

    @Query("DELETE FROM single_tasks WHERE id = :id")
    suspend fun deleteSingleTaskById(id: Int)
}