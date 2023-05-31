package ru.tsu.tasksapp.app.task.regular.add

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.task.dialog.PeriodPickerListener
import ru.tsu.tasksapp.app.task.dialog.PickUpPeriodBottomSheetDialog
import ru.tsu.tasksapp.app.task.dialog.PickUpTimeBottomSheetDialog
import ru.tsu.tasksapp.app.task.dialog.TimePickerListener
import ru.tsu.tasksapp.databinding.ActivityAddRegularTaskBinding
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.single.SingleTask

class AddRegularTaskActivity:
    AppCompatActivity(R.layout.activity_add_regular_task),
    TimePickerListener,
    PeriodPickerListener {

    private val viewBinding: ActivityAddRegularTaskBinding by viewBinding()
    private val viewModel: AddRegularTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() = with(viewBinding) {
        intent.extras?.getString("taskName")?.let {
            viewModel.setTaskName(it)
        }

        addRegularTaskBackIcon.setOnClickListener {
            finish()
        }

        regularTaskItemRegular.setOnClickListener {
            PickUpPeriodBottomSheetDialog().show(supportFragmentManager, "")
        }

        regularTaskItemTime.setOnClickListener {
            PickUpTimeBottomSheetDialog().show(supportFragmentManager, "")
        }
    }

    private fun observeData() = with(viewModel) {
        currentTask.observe(this@AddRegularTaskActivity) {
            setupViewByCurrentTaskInfo(it)
        }
    }

    private fun setupViewByCurrentTaskInfo(task: RegularTask) = with(viewBinding) {
        addRegularTaskName.text = task.name
        regularTaskTime.text = task.time
        regularTaskNotificationTime.text = task.notificationTime

        regularTaskDotTime.isVisible = task.time != null
        regularTaskDotNotification.isVisible = task.notificationTime != null

        if(task.time != null) {
            regularTaskTimeIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
        } else {
            regularTaskTimeIcon.clearColorFilter()
        }

        if(task.regularity != null) {
            regularTaskRegularityIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
            regularTaskRegularityText.text = task.regularity
        } else {
            regularTaskRegularityIcon.clearColorFilter()
            regularTaskRegularityText.text = "Регулярность"
        }

        if(task.notificationTime != null) {
            regularTaskNotificationIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.accent))
        } else {
            regularTaskNotificationIcon.clearColorFilter()
        }

        addRegularTaskSaveButton.isVisible =
            task.name != null && task.time != null &&
                    task.regularity != null && task.notificationTime != null &&
                    task.name.isNotEmpty()
    }

    override fun getTime(time: String) {
        viewModel.setTime(time)
    }

    override fun setPeriod(period: String) {
        viewModel.setPeriod(period)
    }
}