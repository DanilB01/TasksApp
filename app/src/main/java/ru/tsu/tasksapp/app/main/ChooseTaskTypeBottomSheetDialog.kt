package ru.tsu.tasksapp.app.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import ru.tsu.tasksapp.app.task.regular.add.ChooseNameRegularTaskActivity
import ru.tsu.tasksapp.app.task.single.addedit.AddEditSingleTaskActivity
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
        initView()
    }

    private fun initView() = with(viewBinding) {
        chooseTypeCloseImage.setOnClickListener {
            dismiss()
        }

        chooseTypeButton.setOnClickListener {
            if(chooseTypeTabLayout.selectedTabPosition == 0) {
                startActivity(
                    Intent(requireActivity(), AddEditSingleTaskActivity::class.java).apply {
                        putExtra("isAddSingleTask", true)
                    }
                )
            } else {
                startActivity(
                    Intent(requireActivity(), ChooseNameRegularTaskActivity::class.java)
                )
            }
            dismiss()
        }
    }
}