package ru.tsu.tasksapp.app.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.tsu.tasksapp.app.login.model.LoginScreenState
import ru.tsu.tasksapp.domain.credential.CredentialRepository
import ru.tsu.tasksapp.domain.session.SessionRepository

class LoginViewModel : ViewModel() {
    private val credentialRepository = CredentialRepository()
    private val sessionRepository = SessionRepository()

    private val _loginScreenState = MutableLiveData<LoginScreenState>()
    val loginScreenState: LiveData<LoginScreenState> = _loginScreenState

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoginCompleted = MutableLiveData<Boolean>()
    val isLoginCompleted: LiveData<Boolean> = _isLoginCompleted

    init {
        _loginScreenState.value = LoginScreenState.REGISTER
    }

    fun onTabSelected(tabPosition: Int?) {
        _loginScreenState.value = when (tabPosition) {
            0 -> LoginScreenState.REGISTER
            1 -> LoginScreenState.LOGIN
            else -> null
        }
    }

    fun onErrorHandled() {
        _errorMessage.value = null
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Пожалуйста, введите все данные"
                return@launch
            }
            when (_loginScreenState.value) {
                LoginScreenState.REGISTER -> register(email, password)
                LoginScreenState.LOGIN -> signIn(email, password)
                null -> Unit
            }
        }
    }

    private suspend fun register(email: String, password: String) {
        val isEmailAlreadyUsed = credentialRepository.isEmailAlreadyUsed(email)
        if (!isEmailAlreadyUsed) {
            credentialRepository.addCredential(email, password)
            sessionRepository.createSession(email)
            _isLoginCompleted.value = true
        } else {
            _errorMessage.value = "Этот email уже используется"
        }
    }

    private suspend fun signIn(email: String, password: String) {
        if (credentialRepository.isCredentialExist(email, password)) {
            sessionRepository.createSession(email)
            _isLoginCompleted.value = true
        } else {
            _errorMessage.value = "Учетная запись не найдена"
        }
    }
}