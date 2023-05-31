package ru.tsu.tasksapp.app.task.regular.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ActivityChooseNameRegularTaskBinding

class ChooseNameRegularTaskActivity : AppCompatActivity(R.layout.activity_choose_name_regular_task) {
    private val viewBinding: ActivityChooseNameRegularTaskBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() = with(viewBinding) {
        chooseNameHome1.setOnClickListener {
            chooseNameEditText.setText(chooseNameHome1.text)
        }

        chooseNameHome2.setOnClickListener {
            chooseNameEditText.setText(chooseNameHome2.text)
        }

        chooseNameHome3.setOnClickListener {
            chooseNameEditText.setText(chooseNameHome3.text)
        }

        chooseNameNextButton.setOnClickListener {
            val taskName = chooseNameEditText.text.toString()
            if(taskName.isEmpty()) {
                Snackbar
                    .make(viewBinding.root, "Введите название задачи", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                startActivity(
                    Intent(this@ChooseNameRegularTaskActivity, AddRegularTaskActivity::class.java).apply {
                        putExtra("taskName", taskName)
                    }
                )
                finish()
            }
        }
    }
}