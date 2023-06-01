package ru.tsu.tasksapp.app.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.databinding.BottomSheetChoosePhotoBinding
import ru.tsu.tasksapp.domain.photo.PhotoItem

class ChoosePhotoBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetChoosePhotoBinding
    private val viewModel: ChoosePhotoViewModel by viewModels()
    private val adapter: PhotoAdapter by lazy {
        PhotoAdapter(object : PhotoListener{
            override fun removePhoto(photo: PhotoItem) {
                viewModel.removePhoto(photo)
            }
        })
    }
    private val contract = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { uri ->
            viewModel.savePhoto(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = BottomSheetChoosePhotoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.choosePhotoCloseImage.setOnClickListener {
            dismiss()
        }
        viewBinding.choosePhotoRecycler.adapter = adapter
        viewBinding.choosePhotoButton.setOnClickListener {
            dismiss()
        }

        viewBinding.choosePhotoFromDeviceButton.setOnClickListener {
            contract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }
}