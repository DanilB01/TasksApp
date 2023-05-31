package ru.tsu.tasksapp.app.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.databinding.BottomSheetPickupDateBinding
import java.text.DateFormat
import java.util.Calendar

class PickUpDateBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetPickupDateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = BottomSheetPickupDateBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(viewBinding) {
        pickupDateCloseImage.setOnClickListener {
            dismiss()
        }

        pickupDateButton.setOnClickListener {
            (requireActivity() as DatePickerListener).getDate(pickupDateCalendarView.date)
            dismiss()
        }
    }
}

fun interface DatePickerListener {
    fun getDate(dateTimestamp: Long)
}