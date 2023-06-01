package ru.tsu.tasksapp.app.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.app.common.DateTimeUtils
import ru.tsu.tasksapp.databinding.BottomSheetPickupDateBinding
import java.text.DateFormat
import java.util.Calendar

class PickUpDateBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetPickupDateBinding
    private var currentTimestamp: Long = 0

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

        pickupDateCalendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            currentTimestamp = DateTimeUtils.getTimestamp(year, month, dayOfMonth)
            pickupDateButton.apply {
                isEnabled = true
                setBackgroundColor(requireActivity().getColor(R.color.accent))
            }
        }

        pickupDateButton.setOnClickListener {
            (requireActivity() as DatePickerListener).getDate(currentTimestamp)
            dismiss()
        }
    }
}

fun interface DatePickerListener {
    fun getDate(dateTimestamp: Long)
}