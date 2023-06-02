package ru.tsu.tasksapp.app.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.main.calendar.CalendarFragment
import ru.tsu.tasksapp.app.main.home.HomeFragment
import ru.tsu.tasksapp.app.main.tasks.ListRegularTasksFragment
import ru.tsu.tasksapp.app.menu.MenuActivity
import ru.tsu.tasksapp.app.photo.ChoosePhotoBottomSheetDialog
import ru.tsu.tasksapp.app.photo.WishAddPhotoListener
import ru.tsu.tasksapp.app.task.dialog.DatePickerListener
import ru.tsu.tasksapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main),
    WishAddPhotoListener,
    DatePickerListener {

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
            when (it.itemId) {
                R.id.item_main_home -> {
                    mainTitle.text = "Главная"
                    replaceFragment(HomeFragment())
                }
                R.id.item_main_calendar -> {
                    mainTitle.text = "Календарь"
                    replaceFragment(CalendarFragment())
                }
                R.id.item_main_tasks -> {
                    mainTitle.text = "Завершенные задачи"
                    replaceFragment(ListRegularTasksFragment())
                }
            }
            true
        }

        mainBottomNav.selectedItemId = R.id.item_main_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .commit {
                replace(
                    viewBinding.mainFragmentContainer.id,
                    fragment,
                    "listRegularTask"
                )
            }
    }

    override fun addPhoto() {
        ChoosePhotoBottomSheetDialog().show(supportFragmentManager, "")
    }

    override fun getDate(dateTimestamp: Long) {
        (supportFragmentManager.findFragmentByTag("listRegularTask") as ListRegularTasksFragment)
            .setTimestamp(dateTimestamp)
    }
}