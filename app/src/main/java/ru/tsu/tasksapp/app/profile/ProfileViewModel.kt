package ru.tsu.tasksapp.app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.session.SessionRepository

class ProfileViewModel: ViewModel() {
    private val sessionRepository = SessionRepository()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    init {
        viewModelScope.launch {
            getProfile()
        }
    }

    private suspend fun getProfile() {
        _email.value = sessionRepository.getEmailFromSession()
    }
}