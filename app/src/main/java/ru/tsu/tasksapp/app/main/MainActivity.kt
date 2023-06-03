package ru.tsu.tasksapp.app.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.main.calendar.CalendarFragment
import ru.tsu.tasksapp.app.main.home.HomeFragment
import ru.tsu.tasksapp.app.main.tasks.ListRegularTasksFragment
import ru.tsu.tasksapp.app.menu.MenuActivity
import ru.tsu.tasksapp.app.notification.NotificationBroadcastReceiver
import ru.tsu.tasksapp.app.notification.messageExtra
import ru.tsu.tasksapp.app.notification.notificationId
import ru.tsu.tasksapp.app.notification.titleExtra
import ru.tsu.tasksapp.app.photo.ChoosePhotoBottomSheetDialog
import ru.tsu.tasksapp.app.photo.WishAddPhotoListener
import ru.tsu.tasksapp.app.task.dialog.DatePickerListener
import ru.tsu.tasksapp.databinding.ActivityMainBinding
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class MainActivity : AppCompatActivity(R.layout.activity_main),
    WishAddPhotoListener,
    DatePickerListener {

    private val viewBinding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        viewModel.tasksToSetNotification.observe(this) {
            scheduleNotification(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTasksToSetNotifications()
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

    private fun scheduleNotification(tasks: List<RegularTask>) {
        tasks.forEach {
            val intent = Intent(applicationContext, NotificationBroadcastReceiver::class.java)
            val title = "Пора выполнить задание!"
            val message = "Нужно выполнить задание: ${it.name}"
            intent.putExtra(titleExtra, title)
            intent.putExtra(messageExtra, message)

            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                notificationId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val timeSplit = it.notificationTime?.split(":") ?: return
            val hours = timeSplit[0].toInt()
            val minutes = timeSplit[1].toInt()
            val time = DateTimeUtils.getTimestampForTime(System.currentTimeMillis(), hours, minutes)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
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