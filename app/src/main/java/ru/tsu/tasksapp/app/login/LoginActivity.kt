package ru.tsu.tasksapp.app.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.login.model.LoginScreenState
import ru.tsu.tasksapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewBinding: ActivityLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeData()
    }

    private fun observeData() = with(viewModel) {
        loginScreenState.observe(this@LoginActivity) {
            when (it) {
                LoginScreenState.REGISTER -> setRegisterInfo()
                LoginScreenState.LOGIN -> setLoginInfo()
            }
        }

        errorMessage.observe(this@LoginActivity) {
            it?.let { message ->
                Snackbar
                    .make(viewBinding.root, message, Snackbar.LENGTH_SHORT)
                    .show()
                onErrorHandled()
            }
        }

        isLoginCompleted.observe(this@LoginActivity) {
            if (it) finish()
        }
    }

    private fun initView() = with(viewBinding) {
        loginTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.onTabSelected(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        loginButton.setOnClickListener {
            viewModel.login(
                emailText.text.toString(),
                passwordText.text.toString()
            )
        }

        continueButton.setOnClickListener {
            finish()
        }
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