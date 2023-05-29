package ru.tsu.tasksapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityLoginBinding
import ru.tsu.tasksapp.login.model.LoginScreenState

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewBinding: ActivityLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTabLayout()
        observeData()
    }

    private fun observeData() {
        viewModel.loginScreenState.observe(this) {
            when (it) {
                LoginScreenState.REGISTER -> setRegisterInfo()
                LoginScreenState.LOGIN -> setLoginInfo()
            }
        }
    }

    private fun setupTabLayout() {
        viewBinding.loginTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.onTabSelected(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun setRegisterInfo() {
        with(viewBinding) {
            loginTitle.text = "Создайте аккаунт"
            loginSubtitleText.isVisible = true
            loginSpannableText.isVisible = true

        }
    }

    private fun setLoginInfo() {
        with(viewBinding) {
            loginTitle.text = "Вход в аккаунт"
            loginSubtitleText.isVisible = false
            loginSpannableText.isVisible = false
        }
    }
}