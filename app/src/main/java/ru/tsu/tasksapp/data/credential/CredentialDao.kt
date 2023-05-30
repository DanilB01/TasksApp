package ru.tsu.tasksapp.data.credential

import androidx.room.*

@Dao
interface CredentialDao {
    @Query("SELECT * FROM credentials WHERE email = :email LIMIT 1")
    suspend fun getCredentialByEmail(email: String): CredentialEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCredential(credential: CredentialEntity)

    @Delete
    suspend fun deleteCredential(credential: CredentialEntity)
}