package ru.tsu.tasksapp.app.task.single.addedit

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.notification.*
import ru.tsu.tasksapp.app.task.dialog.DatePickerListener
import ru.tsu.tasksapp.app.task.dialog.PickUpDateBottomSheetDialog
import ru.tsu.tasksapp.app.task.dialog.PickUpTimeBottomSheetDialog
import ru.tsu.tasksapp.app.task.dialog.TimePickerListener
import ru.tsu.tasksapp.databinding.ActivityAddEditSingleTaskBinding
import ru.tsu.tasksapp.domain.task.single.SingleTask

class AddEditSingleTaskActivity :
    AppCompatActivity(R.layout.activity_add_edit_single_task),
    TimePickerListener,
    DatePickerListener {

    private val viewBinding: ActivityAddEditSingleTaskBinding by viewBinding()
    private val viewModel: AddEditSingleTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() = with(viewBinding) {
        intent.extras?.getString("currentTask")?.let {
            // TODO: add init current task to edit
        }

        addEditSingleTaskBackIcon.setOnClickListener {
            finish()
        }
        intent.extras?.getBoolean("isAddSingleTask")?.let {
            addEditSingleTaskSubtitle.isVisible = !it
            addEditSingleTaskTitle.text = if(it) {
                "Новая разовая задача"
            } else {
                "Разовая задача"
            }
        }

        singleTaskItemTime.setOnClickListener {
            viewModel.setIsNotificationTimeSelecting(false)
            PickUpTimeBottomSheetDialog().show(supportFragmentManager, "")
        }

        singleTaskItemNotification.setOnClickListener {
            viewModel.setIsNotificationTimeSelecting(true)
            PickUpTimeBottomSheetDialog().show(supportFragmentManager, "")
        }

        singleTaskItemDate.setOnClickListener {
            PickUpDateBottomSheetDialog().show(supportFragmentManager, "")
        }

        addEditSingleTaskName.doOnTextChanged { text, start, before, count ->
            viewModel.updateTaskName(text.toString())
        }

        addEditSaveButton.setOnClickListener {
            viewModel.saveSingleTask()
            createNotificationChannel()
            scheduleNotification()
            finish()
        }
    }

    private fun observeData() = with(viewModel) {
        currentTask.observe(this@AddEditSingleTaskActivity) {
            setupViewByCurrentTaskInfo(it)
        }
    }

    private fun setupViewByCurrentTaskInfo(task: SingleTask) = with(viewBinding) {
        singleTaskTime.text = task.time
        singleTaskDate.text = task.date
        singleTaskNotificationTime.text = task.notificationTime

        singleTaskDotTime.isVisible = task.time != null
        singleTaskDotDate.isVisible = task.date != null
        singleTaskDotNotification.isVisible = task.notificationTime != null

        if(task.time != null) {
            singleTaskTimeIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
        } else {
            singleTaskTimeIcon.clearColorFilter()
        }

        if(task.date != null) {
            singleTaskDateIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
        } else {
            singleTaskDateIcon.clearColorFilter()
        }

        if(task.notificationTime != null) {
            singleTaskNotificationIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
        } else {
            singleTaskNotificationIcon.clearColorFilter()
        }

        addEditSaveButton.isVisible =
            task.name != null && task.time != null &&
                task.date != null && task.notificationTime != null &&
                    task.name.isNotEmpty()
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, NotificationBroadcastReceiver::class.java)
        val title = "Пора выполнить задание!"
        val message = "Нужно выполнить задание: ${viewModel.currentTask.value?.name}"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeSplit = viewModel.currentTask.value?.notificationTime?.split(":") ?: return
        val hours = timeSplit[0].toInt()
        val minutes = timeSplit[1].toInt()
        val time = DateTimeUtils.getTimestampForTime(viewModel.currentTask.value?.dateTimestamp!!, hours, minutes)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        val name = "Task channel"
        val description = "Notification channel for tasks"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun getTime(time: String) {
        viewModel.setTime(time)
    }

    override fun getDate(dateTimestamp: Long) {
        viewModel.setDate(dateTimestamp)
    }
}