package ru.tsu.tasksapp.app.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ItemHomeTaskBinding
import ru.tsu.tasksapp.domain.common.TaskInfo
import ru.tsu.tasksapp.domain.task.regular.RegularTask

class HomeTaskItemAdapter : RecyclerView.Adapter<HomeTaskItemAdapter.HomeTaskItemViewHolder>() {

    private var items: List<TaskInfo> = emptyList()

    fun update(newItems: List<TaskInfo>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class HomeTaskItemViewHolder(view: View) : ViewHolder(view) {
        private val binding: ItemHomeTaskBinding by lazy { ItemHomeTaskBinding.bind(view) }

        fun bind(item: TaskInfo) = with(binding) {
            itemHomeTaskCheckbox.text = item.name
            itemHomeTaskDateText.text = item.date

            itemHomeRegularTaskIcon.isVisible = item.task is RegularTask
            itemHoneTaskRegularityText.isVisible = item.task is RegularTask
            (item.task as? RegularTask)?.let {
                itemHoneTaskRegularityText.text = it.regularity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTaskItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_task, parent, false)
        return HomeTaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeTaskItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
