package ru.tsu.tasksapp.app.photo

import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.ItemPhotoBinding
import ru.tsu.tasksapp.domain.photo.PhotoItem

class PhotoAdapter(
    private val listener: PhotoListener? = null,
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var items: List<PhotoItem> = emptyList()

    fun update(newItems: List<PhotoItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemPhotoBinding by lazy { ItemPhotoBinding.bind(view) }

        fun bind(item: PhotoItem) = with(binding) {
            val bitmap = MediaStore.Images.Media.getBitmap(root.context.contentResolver, item.uri.toUri())
            photoImage.setImageBitmap(bitmap)
            photoName.text = item.name
            photoSize.text = item.size
            protoRemoveImage.setOnClickListener {
                listener?.removePhoto(item)
            }
            protoRemoveImage.isVisible = listener != null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

interface PhotoListener {
    fun removePhoto(photo: PhotoItem)
}
