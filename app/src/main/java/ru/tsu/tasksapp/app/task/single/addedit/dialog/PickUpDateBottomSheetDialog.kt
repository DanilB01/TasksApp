package ru.tsu.tasksapp.app.task.single.addedit.dialog

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
    private val calendar: Calendar by lazy { Calendar.getInstance() }

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
            calendar.timeInMillis = pickupDateCalendarView.date
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            (requireActivity() as DatePickerListener).getDate(dateFormatter.format(calendar.time))
            dismiss()
        }
    }
}

fun interface DatePickerListener {
    fun getDate(date: String)
}