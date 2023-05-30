package ru.tsu.tasksapp.domain.credential

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.credential.CredentialEntity

class CredentialRepository {
    suspend fun isEmailAlreadyUsed(email: String): Boolean =
        Database.getCredentialDao().getCredentialByEmail(email) != null

    suspend fun isCredentialExist(email: String, password: String): Boolean {
        val credential = Database.getCredentialDao().getCredentialByEmail(email) ?: return false
        return credential.password == password
    }

    suspend fun addCredential(
        email: String,
        password: String
    ) {
        Database.getCredentialDao().addCredential(
            CredentialEntity(
                email = email,
                password = password
            )
        )
    }
}