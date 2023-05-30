package ru.tsu.tasksapp.app.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.login.LoginActivity
import ru.tsu.tasksapp.app.profile.ProfileActivity
import ru.tsu.tasksapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(R.layout.activity_menu) {

    private val viewBinding: ActivityMenuBinding by viewBinding()
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkIsLoggedIn()
    }

    private fun initView() = with(viewBinding) {
        window.statusBarColor = resources.getColor(R.color.accent_yellow_light)

        menuItemAuth.setOnClickListener {
            viewModel.isLoggedIn.value?.let {
                if (it) {
                    viewModel.onLogoutCLicked()
                } else {
                    startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
                }
            }
        }

        menuItemProfile.setOnClickListener {
            viewModel.isLoggedIn.value?.let {
                if (it) {
                    startActivity(Intent(this@MenuActivity, ProfileActivity::class.java))
                } else {
                    startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
                }
            }
        }
    }

    private fun observeData() = with(viewModel) {
        isLoggedIn.observe(this@MenuActivity) {
            if (it) {
                setLoggedInfo()
            } else {
                setWithoutLoginInfo()
            }
        }
    }

    private fun setLoggedInfo() = with(viewBinding) {
        menuProfileImage.visibility = View.INVISIBLE
        menuTasksTitle.text = "Завершенные задачи"
        menuProfileTitle.text = "Профиль"
        menuAuthTitle.text = "Выход"
        menuAuthIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_logout))
    }

    private fun setWithoutLoginInfo() = with(viewBinding) {
        menuProfileImage.visibility = View.VISIBLE
        menuTasksTitle.text = "Все завершенные задачи"
        menuProfileTitle.text = "Авторизоваться"
        menuAuthTitle.text = "Зарегистрироваться"
        menuAuthIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_login))
    }
}