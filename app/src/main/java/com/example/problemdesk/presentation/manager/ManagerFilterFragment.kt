package com.example.problemdesk.presentation.manager

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.problemdesk.databinding.FragmentManagerFilterBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale

class ManagerFilterFragment : Fragment() {

    private var _binding: FragmentManagerFilterBinding? = null
    private val binding get() = _binding!!

    private val managerFilterViewModel: ManagerFilterViewModel by viewModels()

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerFilterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO графики

        setUpDatePicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {

    }

    private fun showContent() {

    }

    private fun showPlug() {

    }

    private fun setUpDatePicker() {
        binding.chartDateFilterPicker.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            builder.setTitleText("Выберите временной диапазон:")

            val constraintBuilder = CalendarConstraints.Builder()
//                .setStart(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            builder.setCalendarConstraints(constraintBuilder)
            val dateRangerPicker = builder.build()
            dateRangerPicker.show(requireActivity().supportFragmentManager, "date_range_picker")

            dateRangerPicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second
                updateLabel(startDate, endDate)
            }
        }
    }

//TODO data validation

    private fun updateLabel(startDate: Long, endDate: Long) {
        val startCalendar = Calendar.getInstance().apply { timeInMillis = startDate }
        val endCalendar = Calendar.getInstance().apply { timeInMillis = endDate }
        val dateFormat = SimpleDateFormat("MM.dd.yyyy", Locale.getDefault())
        val formattedStartDate = dateFormat.format(startCalendar.time)
        val formattedEndDate = dateFormat.format(endCalendar.time)
        binding.chartDateFilterPicker.setText("$formattedStartDate - $formattedEndDate")
    }

    private fun setUpObservers() {

    }

    private fun loadChart() {

    }

    private fun CLICKLISTENERS() {

    }
}