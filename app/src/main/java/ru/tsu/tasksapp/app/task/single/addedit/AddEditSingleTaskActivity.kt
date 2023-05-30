package ru.tsu.tasksapp.app.task.single.addedit

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.task.single.addedit.dialog.DatePickerListener
import ru.tsu.tasksapp.app.task.single.addedit.dialog.PickUpDateBottomSheetDialog
import ru.tsu.tasksapp.app.task.single.addedit.dialog.PickUpTimeBottomSheetDialog
import ru.tsu.tasksapp.app.task.single.addedit.dialog.TimePickerListener
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
        addEditSingleTaskBackIcon.setOnClickListener {
            finish()
        }
        intent.extras?.getBoolean("isAddSingleTask")?.let {
            addEditSingleTaskSubtitle.isVisible = !it
            singleTaskItemMakeRegular.isVisible = it
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

    override fun getTime(time: String) {
        viewModel.setTime(time)
    }

    override fun getDate(date: String) {
        viewModel.setDate(date)
    }
}