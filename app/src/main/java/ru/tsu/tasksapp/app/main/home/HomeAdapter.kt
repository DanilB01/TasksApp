package ru.tsu.tasksapp.app.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.main.TaskItemAdapter
import ru.tsu.tasksapp.databinding.ItemHomeBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var items: List<HomeItem> = emptyList()

    fun update(newItems: List<HomeItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemHomeBinding by lazy { ItemHomeBinding.bind(view) }
        private val adapter: TaskItemAdapter by lazy { TaskItemAdapter() }

        init {
            binding.itemHomeRecycler.adapter = adapter
        }

        fun bind(item: HomeItem) = with(binding) {
            itemHomeStatusText.text = item.title
            itemHomeStatusText.setTextColor(root.context.resources.getColor(item.color))
            itemHomeAmountText.text = item.tasks.size.toString()
            itemHomeAmountText.setTextColor(root.context.resources.getColor(item.color))
            adapter.update(item.tasks)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_home, viewGroup, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: HomeViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size
}