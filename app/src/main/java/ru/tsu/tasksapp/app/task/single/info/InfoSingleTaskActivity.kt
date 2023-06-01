package ru.tsu.tasksapp.app.task.single.info

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.photo.PhotoAdapter
import ru.tsu.tasksapp.app.photo.PhotoItem
import ru.tsu.tasksapp.app.photo.PhotoListener
import ru.tsu.tasksapp.app.photo.WishAddPhotoBottomSheetDialog
import ru.tsu.tasksapp.databinding.ActivityInfoSingleTaskBinding
import ru.tsu.tasksapp.domain.task.TaskStatus

class InfoSingleTaskActivity : AppCompatActivity(R.layout.activity_info_single_task) {
    private val viewBinding: ActivityInfoSingleTaskBinding by viewBinding()
    private val viewModel: InfoSingleTaskViewModel by viewModels()
    private val adapter: PhotoAdapter by lazy {
        PhotoAdapter(object : PhotoListener {
            override fun removePhoto(photo: PhotoItem) {
                //TODO("Not yet implemented")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getInt("taskId")?.let {
            viewModel.loadTaskInfo(it)
        }

        viewBinding.photoSingleRecycler.adapter = adapter

        viewBinding.infoSingleTaskDoneButton.setOnClickListener {
            viewModel.setTaskDone()
        }

        viewBinding.infoSingleRemoveTaskButton.setOnClickListener {
            if(viewModel.currentTask.value == null) return@setOnClickListener
            viewModel.deleteTask()
            finish()
        }

        viewBinding.infoSingleTaskBackIcon.setOnClickListener {
            finish()
        }

        viewModel.currentTask.observe(this) {
            viewBinding.infoSingleTaskName.text = it.name
            viewBinding.infoSingleTaskTime.text = it.time
            viewBinding.infoSingleTaskDate.text = it.date
            viewBinding.infoSingleTaskNotificationTime.text = it.notificationTime
            viewBinding.infoTaskStatus.setImageDrawable(
                resources.getDrawable(
                    when (it.status) {
                        TaskStatus.ACTIVE -> R.drawable.pic_status_active
                        TaskStatus.DONE -> R.drawable.pic_status_done
                    }
                )
            )
            viewBinding.infoSingleTaskDoneButton.isVisible = it.status != TaskStatus.DONE
            viewBinding.infoSinglePhotoCard.isVisible =
                viewModel.isPhotosVisible.value == true && it.status == TaskStatus.DONE
        }

        viewModel.isOverdue.observe(this) {
            if (it && viewModel.currentTask.value?.status == TaskStatus.ACTIVE) {
                viewBinding.infoTaskStatus.setImageDrawable(resources.getDrawable(R.drawable.pic_status_overdue))
            }
        }

        viewModel.isPhotosVisible.observe(this) {
            viewBinding.infoSinglePhotoCard.isVisible = it &&
                    viewModel.currentTask.value?.status == TaskStatus.DONE
        }

        viewModel.photos.observe(this) {
            adapter.update(it)
        }
    }
}