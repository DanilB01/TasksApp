package ru.tsu.tasksapp.app.main.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.app.main.TaskItemAdapter
import ru.tsu.tasksapp.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private val viewBinding: FragmentCalendarBinding by viewBinding()
    private val viewModel: CalendarViewModel by viewModels()
    private val adapter: TaskItemAdapter by lazy { TaskItemAdapter() }

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
}