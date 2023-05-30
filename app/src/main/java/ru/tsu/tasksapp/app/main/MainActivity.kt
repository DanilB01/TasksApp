package ru.tsu.tasksapp.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityMainBinding
import ru.tsu.tasksapp.app.menu.MenuActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding: ActivityMainBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.mainMenuIcon.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}