package ru.tsu.tasksapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tsu.tasksapp.login.model.LoginScreenState

class LoginViewModel: ViewModel() {
    private val _loginScreenState = MutableLiveData<LoginScreenState>()
    val loginScreenState: LiveData<LoginScreenState> = _loginScreenState

    init {
        _loginScreenState.value = LoginScreenState.REGISTER
    }

    fun onTabSelected(tabPosition: Int?) {
        _loginScreenState.value = when(tabPosition) {
            0 -> LoginScreenState.REGISTER
            1 -> LoginScreenState.LOGIN
            else -> null
        }
    }
}