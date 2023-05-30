package ru.tsu.tasksapp.domain.session

import ru.tsu.tasksapp.data.common.Database
import ru.tsu.tasksapp.data.session.SessionEntity

class SessionRepository {
    suspend fun createSession(email: String) = with(Database.getSessionDao()) {
        clearSessions()
        addSession(SessionEntity(email))
    }

    suspend fun getEmailFromSession(): String? = Database.getSessionDao().getSession()?.email

    suspend fun clearSession() {
        Database.getSessionDao().clearSessions()
    }
}