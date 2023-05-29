package ru.tsu.tasksapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewBinding: ActivityLoginBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}