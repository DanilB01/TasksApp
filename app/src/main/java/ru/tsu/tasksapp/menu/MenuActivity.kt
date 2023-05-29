package ru.tsu.tasksapp.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityMenuBinding
import ru.tsu.tasksapp.login.LoginActivity

class MenuActivity : AppCompatActivity(R.layout.activity_menu) {

    private val viewBinding: ActivityMenuBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = resources.getColor(R.color.accent_yellow_light)

        viewBinding.menuItemAuth.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}