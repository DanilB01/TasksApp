package ru.tsu.tasksapp.app.task.regular.info

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.photo.*
import ru.tsu.tasksapp.domain.photo.PhotoItem
import ru.tsu.tasksapp.databinding.ActivityInfoRegularTaskBinding
import ru.tsu.tasksapp.domain.task.TaskStatus

class InfoRegularTaskActivity : AppCompatActivity(R.layout.activity_info_regular_task),
    WishAddPhotoListener {
    private val viewBinding: ActivityInfoRegularTaskBinding by viewBinding()
    private val viewModel: InfoRegularTaskViewModel by viewModels()
    private val adapter: PhotoAdapter by lazy { PhotoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getInt("taskId")?.let {
            viewModel.loadTaskInfo(it)
        }

        viewBinding.photoRegularRecycler.adapter = adapter

        viewBinding.infoRegularTaskBackIcon.setOnClickListener {
            finish()
        }

        viewBinding.infoRegularRemoveTaskButton.setOnClickListener {
            if (viewModel.currentTask.value == null) return@setOnClickListener
            viewModel.deleteTask()
            finish()
        }

        viewBinding.infoRegularTaskDoneForTodayButton.setOnClickListener {
            if (viewModel.currentTask.value == null) return@setOnClickListener
            viewModel.setTaskDoneForToday()
        }

        viewBinding.infoRegularTaskDoneButton.setOnClickListener {
            if (viewModel.currentTask.value == null) return@setOnClickListener
            viewModel.setTaskDone()
        }

        observeData()
    }

    private fun observeData() = with(viewModel) {
        currentTask.observe(this@InfoRegularTaskActivity) {
            viewBinding.infoRegularTaskName.text = it.name
            viewBinding.regularTaskTime.text = it.time
            viewBinding.regularTaskNotificationTime.text = it.notificationTime
            viewBinding.regularTaskRegularityText.text = it.regularity
            viewBinding.infoRegularTaskDoneButton.isVisible = it.status != TaskStatus.DONE
        }

        isTaskActiveForToday.observe(this@InfoRegularTaskActivity) {
            viewBinding.infoRegularPhotoCard.isVisible = !it &&
                    viewModel.isPhotosVisible.value == true
            if(it) {
                viewBinding.infoRegularTaskDoneForTodayButton.apply {
                    text = "Выполнить"
                    setBackgroundColor(
                        resources.getColor(R.color.accent)
                    )
                    isClickable = true

                }
            } else {
                viewBinding.infoRegularTaskDoneForTodayButton.apply {
                    text = "Выполнена"
                    setBackgroundColor(
                        resources.getColor(R.color.accent_green)
                    )
                    isClickable = false

                }
            }
        }

        isPhotosVisible.observe(this@InfoRegularTaskActivity) {
            viewBinding.infoRegularPhotoCard.isVisible = it &&
                    viewModel.isTaskActiveForToday.value == false
        }

        viewModel.photos.observe(this@InfoRegularTaskActivity) {
            adapter.update(it)
        }

        viewModel.isShowAddPhotoDialog.observe(this@InfoRegularTaskActivity) {
            if (it) WishAddPhotoBottomSheetDialog().show(supportFragmentManager, "")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadPhotos()
    }

    override fun addPhoto() {
        ChoosePhotoBottomSheetDialog().show(supportFragmentManager, "")
    }
}