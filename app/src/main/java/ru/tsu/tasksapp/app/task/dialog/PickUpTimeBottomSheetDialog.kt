package ru.tsu.tasksapp.app.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.databinding.BottomSheetPickupTimeBinding


class PickUpTimeBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var viewBinding: BottomSheetPickupTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = BottomSheetPickupTimeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(viewBinding) {
        pickupTimeCloseImage.setOnClickListener {
            dismiss()
        }

        pickupTimeButton.setOnClickListener {
            var hours = pickupTimeHours.text.toString()
            if(hours.isEmpty() || hours.toInt() > 23) {
                hours = "00"
            }
            var minutes = pickupTimeMinutes.text.toString()
            if(minutes.isEmpty() || minutes.toInt() > 59) {
                minutes = "00"
            }
            (requireActivity() as TimePickerListener).getTime("$hours:$minutes")
            dismiss()
        }
    }
}

fun interface TimePickerListener {
    fun getTime(time: String)
}