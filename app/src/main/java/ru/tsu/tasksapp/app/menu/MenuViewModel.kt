package ru.tsu.tasksapp.app.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.domain.session.SessionRepository

class MenuViewModel: ViewModel() {
    private val sessionRepository = SessionRepository()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    init {
        checkIsLoggedIn()
    }

    fun checkIsLoggedIn() {
        viewModelScope.launch {
            _isLoggedIn.value = sessionRepository.getEmailFromSession() != null
        }
    }

    fun onLogoutCLicked() {
        viewModelScope.launch {
            sessionRepository.clearSession()
            _isLoggedIn.value = sessionRepository.getEmailFromSession() != null
        }
    }
}