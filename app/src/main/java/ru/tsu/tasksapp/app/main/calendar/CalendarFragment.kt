package ru.tsu.tasksapp.app.main.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.main.TaskItemAdapter
import ru.tsu.tasksapp.app.main.TaskItemListener
import ru.tsu.tasksapp.app.photo.TaskValues
import ru.tsu.tasksapp.app.task.regular.info.InfoRegularTaskActivity
import ru.tsu.tasksapp.app.task.regular.info.InfoRegularTaskViewModel
import ru.tsu.tasksapp.app.task.single.info.InfoSingleTaskActivity
import ru.tsu.tasksapp.databinding.FragmentCalendarBinding
import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.single.SingleTask

class CalendarFragment : Fragment(R.layout.fragment_calendar), TaskItemListener {
    private val viewBinding: FragmentCalendarBinding by viewBinding()
    private val viewModel: CalendarViewModel by viewModels()
    private val adapter: TaskItemAdapter by lazy { TaskItemAdapter(this, true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.calendarTasksRecycler.adapter = adapter

        viewBinding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val timestamp = DateTimeUtils.getTimestamp(year, month, dayOfMonth)
            viewModel.updateTasks(timestamp)
            viewBinding.calendarDateText.text = DateTimeUtils.getDateString(timestamp)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    override fun onTaskDone(task: Task) = Unit

    override fun onTaskClicked(task: Task) {
        when (task) {
            is SingleTask -> {
                TaskValues.setValues(
                    currentTaskId = task.id,
                    isForSingleTask = true
                )
                startActivity(
                    Intent(requireContext(), InfoSingleTaskActivity::class.java).apply {
                        putExtra("taskId", task.id)
                    }
                )
            }
            is RegularTask -> {
                TaskValues.setValues(
                    currentTaskId = task.id,
                    isForSingleTask = true
                )
                startActivity(
                    Intent(requireContext(), InfoRegularTaskActivity::class.java).apply {
                        putExtra("taskId", task.id)
                    }
                )
            }
            else -> return
        }
    }
}