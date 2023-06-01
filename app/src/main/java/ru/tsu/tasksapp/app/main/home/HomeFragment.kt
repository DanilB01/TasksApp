package ru.tsu.tasksapp.app.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.main.TaskItemListener
import ru.tsu.tasksapp.app.task.single.info.InfoSingleTaskActivity
import ru.tsu.tasksapp.databinding.FragmentHomeBinding
import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.regular.RegularTask
import ru.tsu.tasksapp.domain.task.single.SingleTask

class HomeFragment : Fragment(R.layout.fragment_home), TaskItemListener {
    private val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeAdapter by lazy { HomeAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.adapter = adapter

        viewModel.homeItems.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateHomeItems()
    }

    override fun onTaskDone(task: Task) {
        viewModel.markTaskDone(task)
    }

    override fun onTaskClicked(task: Task) {
        when (task) {
            is SingleTask -> {
                startActivity(
                    Intent(requireContext(), InfoSingleTaskActivity::class.java).apply {
                        putExtra("taskId", task.id)
                    }
                )
            }
            is RegularTask -> {}
            else -> return
        }
    }
}