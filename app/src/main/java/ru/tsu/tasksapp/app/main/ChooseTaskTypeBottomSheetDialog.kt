package ru.tsu.tasksapp.app.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
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

        chooseTypeTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                chooseTypeButton.isEnabled = tab?.position == 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        chooseTypeButton.setOnClickListener {
            startActivity(
                Intent(requireActivity(), AddEditSingleTaskActivity::class.java).apply {
                    putExtra("isAddSingleTask", true)
                }
            )
            dismiss()
        }
    }
}