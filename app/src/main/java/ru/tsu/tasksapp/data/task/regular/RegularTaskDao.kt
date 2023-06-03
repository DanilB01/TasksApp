package ru.tsu.tasksapp.data.task.regular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RegularTaskDao {
    @Query("SELECT * FROM regular_tasks")
    suspend fun getRegularTasks(): List<RegularTaskEntity>?

    @Query("SELECT * FROM regular_tasks WHERE id = :id LIMIT 1")
    suspend fun getRegularTaskById(id: Int): RegularTaskEntity?

    @Insert
    suspend fun addRegularTask(task: RegularTaskEntity)

    @Update
    suspend fun updateRegularTask(task: RegularTaskEntity)

    @Query("UPDATE regular_tasks SET status = :status WHERE id = :id")
    suspend fun markTaskDone(id: Int, status: String)

    @Query("UPDATE regular_tasks SET currentTaskDoneTimestamp = :timestamp WHERE id = :id")
    suspend fun setCurrentDoneDate(id: Int, timestamp: String)

    @Query("UPDATE regular_tasks SET lastSettingNotificationTimestamp = :timestamp WHERE id = :id")
    suspend fun setLastSettingNotificationDate(id: Int, timestamp: String)

    @Query("DELETE FROM regular_tasks WHERE id = :id")
    suspend fun deleteRegularTasksById(id: Int)
}