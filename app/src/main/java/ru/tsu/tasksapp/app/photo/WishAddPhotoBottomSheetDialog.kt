package ru.tsu.tasksapp.app.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.databinding.BottomSheetWishAddPhotoBinding

class WishAddPhotoBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetWishAddPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = BottomSheetWishAddPhotoBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.wishCloseImage.setOnClickListener {
            dismiss()
        }

        viewBinding.wishYesButton.setOnClickListener {
            (requireActivity() as WishAddPhotoListener).addPhoto()
            dismiss()
        }

        viewBinding.wishNoButton.setOnClickListener {
            dismiss()
        }
    }
}

interface WishAddPhotoListener {
    fun addPhoto()
}