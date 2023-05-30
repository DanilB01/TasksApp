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

        initView()
    }

    private fun initView() = with(viewBinding) {
        mainMenuIcon.setOnClickListener {
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
        }

        addTasksFloatingButton.setOnClickListener {
            ChooseTaskTypeBottomSheetDialog().show(supportFragmentManager, "")
        }

        mainBottomNav.setOnItemSelectedListener {
            mainTitle.text = when (it.itemId) {
                R.id.item_main_home -> "Главная"
                R.id.item_main_calendar -> "Календарь"
                R.id.item_main_tasks -> "Регулярные задачи"
                else -> ""
            }
            true
        }

        mainBottomNav.selectedItemId = R.id.item_main_home
    }
}