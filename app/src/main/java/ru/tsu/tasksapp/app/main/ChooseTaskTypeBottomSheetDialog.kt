package ru.tsu.tasksapp.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.databinding.BottomSheetChooseTaskTypeBinding

class ChooseTaskTypeBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetChooseTaskTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = BottomSheetChooseTaskTypeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.chooseTypeCloseImage.setOnClickListener {
            dismiss()
        }
    }
}