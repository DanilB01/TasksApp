package ru.tsu.tasksapp.data.credential

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val password: String
)