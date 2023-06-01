package ru.tsu.tasksapp.app.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ItemHomeTaskBinding
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.task.Task
import ru.tsu.tasksapp.domain.task.TaskStatus
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class TaskItemAdapter(
    private val listener: TaskItemListener,
    private val isCalendarScreen: Boolean
) : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {

    private var items: List<TaskInfo> = emptyList()

    fun update(newItems: List<TaskInfo>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class TaskItemViewHolder(view: View) : ViewHolder(view) {
        private val binding: ItemHomeTaskBinding by lazy { ItemHomeTaskBinding.bind(view) }

        init {
            if (isCalendarScreen) binding.itemHomeTaskCheckbox.isClickable = false
        }

        fun bind(item: TaskInfo) = with(binding) {
            itemHomeTaskNameText.text = item.name
            itemHomeTaskDateText.text = if (item.task is RegularTask){
                "до ${item.date}"
            } else {
                item.date
            }

            itemHomeRegularTaskIcon.isVisible = item.task is RegularTask
            itemHoneTaskRegularityText.isVisible = item.task is RegularTask
            (item.task as? RegularTask)?.let {
                itemHoneTaskRegularityText.text = it.regularity
            }

            if(item.status == TaskStatus.DONE) {
                itemHomeTaskCheckbox.isChecked = true
                itemHomeTaskCheckbox.isEnabled = false
                itemHomeTaskDateText.setTextColor(root.context.resources.getColor(R.color.textLight))
                itemHomeTaskNameText.setTextColor(root.context.resources.getColor(R.color.textLight))
                itemHoneTaskRegularityText.setTextColor(root.context.resources.getColor(R.color.textLight))
                itemHomeTaskEditIcon.setColorFilter(root.context.resources.getColor(R.color.textLight))
            } else {
                itemHomeTaskCheckbox.isChecked = false
                itemHomeTaskCheckbox.isEnabled = true
                itemHomeTaskDateText.setTextColor(root.context.resources.getColor(R.color.text))
                itemHomeTaskNameText.setTextColor(root.context.resources.getColor(R.color.text))
                itemHoneTaskRegularityText.setTextColor(root.context.resources.getColor(R.color.text))
                itemHomeTaskEditIcon.setColorFilter(root.context.resources.getColor(R.color.text))
            }

            itemHomeTaskNameText.setOnClickListener {
                listener.onTaskClicked(item.task)
            }

            itemHomeTaskCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) {
                    listener.onTaskDone(item.task)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_task, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

interface TaskItemListener{
    fun onTaskDone(task: Task)
    fun onTaskClicked(task: Task)
}