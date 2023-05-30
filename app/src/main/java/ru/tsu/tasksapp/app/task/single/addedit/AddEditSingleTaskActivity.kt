package ru.tsu.tasksapp.app.task.single.addedit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityAddEditSingleTaskBinding

class AddEditSingleTaskActivity : AppCompatActivity(R.layout.activity_add_edit_single_task) {

    private val viewBinding: ActivityAddEditSingleTaskBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
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
    }
}