package ru.tsu.tasksapp.app.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.BottomSheetPickupPeriodBinding

class PickUpPeriodBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetPickupPeriodBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = BottomSheetPickupPeriodBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(viewBinding) {
        pickupPeriodCloseImage.setOnClickListener {
            dismiss()
        }

        val periodVariants = arrayOf(
            "дней", "недель", "месяцев"
        )
        pickupPeriodPicker.apply {
            minValue = 0
            maxValue = periodVariants.size - 1
            displayedValues = periodVariants
            wrapSelectorWheel = false
        }

        pickupPeriodButton.setOnClickListener {
            var periodValue = pickupPeriodEditText.text.toString()
            if(periodValue.isEmpty()) {
                periodValue = "1"
            }
            (requireActivity() as PeriodPickerListener).setPeriod(
                "Каждые $periodValue ${periodVariants[pickupPeriodPicker.value]}"
            )
            dismiss()
        }
    }

}

fun interface PeriodPickerListener {
    fun setPeriod(period: String)
}