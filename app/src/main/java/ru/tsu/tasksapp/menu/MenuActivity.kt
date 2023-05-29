package ru.tsu.tasksapp.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(R.layout.activity_menu) {

    private val viewBinding: ActivityMenuBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = resources.getColor(R.color.accent_yellow_light)
    }
}