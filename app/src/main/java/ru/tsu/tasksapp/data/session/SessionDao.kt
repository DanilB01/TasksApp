package ru.tsu.tasksapp.data.session

import androidx.room.*

@Dao
interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    suspend fun getSession(): SessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSession(session: SessionEntity)

    @Query("DELETE FROM session")
    suspend fun clearSessions()
}