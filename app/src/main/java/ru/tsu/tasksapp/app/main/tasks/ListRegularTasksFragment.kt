package ru.tsu.tasksapp.app.main.tasks

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
import ru.tsu.tasksapp.app.task.dialog.PickUpDateBottomSheetDialog
import ru.tsu.tasksapp.app.task.regular.info.InfoRegularTaskActivity
import ru.tsu.tasksapp.databinding.FragmentListRegularTasksBinding
import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class ListRegularTasksFragment : Fragment(R.layout.fragment_list_regular_tasks) {

    private val viewBinding: FragmentListRegularTasksBinding by viewBinding()
    private val viewModel: ListRegularTaskViewModel by viewModels()
    private val adapter: TaskItemAdapter by lazy {
        TaskItemAdapter(
            isCalendarScreen = true,
            listener = object : TaskItemListener {
                override fun onTaskDone(task: Task) = Unit

                override fun onTaskClicked(task: Task) {
                    startActivity(
                        Intent(requireContext(), InfoRegularTaskActivity::class.java).apply {
                            putExtra("taskId", (task as RegularTask).id)
                        }
                    )
                }
            }
        )
    }

    private var startTimestamp: Long? = null
    private var endTimestamp: Long? = null

    fun setTimestamp(timestamp: Long) {
        if (startTimestamp == null) {
            startTimestamp = timestamp
            viewBinding.calendarDateText2.text = "${DateTimeUtils.getDateString(startTimestamp!!)} - ..."
            PickUpDateBottomSheetDialog().show(requireActivity().supportFragmentManager, "")
        } else {
            endTimestamp = timestamp
            viewBinding.calendarDateText2.text =
                "${DateTimeUtils.getDateString(startTimestamp!!)} - ${DateTimeUtils.getDateString(endTimestamp!!)}"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.doneRegularTasksRecycler.adapter = adapter
        viewBinding.calendarDateText2.setOnClickListener {
            startTimestamp = null
            endTimestamp = null
            PickUpDateBottomSheetDialog().show(requireActivity().supportFragmentManager, "")
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }
}