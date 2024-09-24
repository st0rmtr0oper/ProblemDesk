package com.example.problemdesk.presentation.manager

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.R
import com.example.problemdesk.data.models.BossRequest
import com.example.problemdesk.databinding.FragmentManagerChartBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.Specialization
import com.example.problemdesk.domain.models.Workplace
import com.example.problemdesk.presentation.general.SpecializationAdapter
import com.example.problemdesk.presentation.general.StatusAdapter
import com.example.problemdesk.presentation.general.WorkplaceAdapter
import com.example.problemdesk.presentation.general.getSpecializationArray
import com.example.problemdesk.presentation.general.getStatusArray
import com.example.problemdesk.presentation.general.getStatusForCharts
import com.example.problemdesk.presentation.general.getWorkplaceArray
import com.example.problemdesk.presentation.login.LoginViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class ManagerChartFragment : Fragment() {

    private var _binding: FragmentManagerChartBinding? = null
    private val binding get() = _binding!!

    private val managerChartViewModel: ManagerChartViewModel by lazy {
        ViewModelProvider(this)[ManagerChartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerChartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showFilter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSpinners()
        setUpDatePicker()

        setUpObservers()
        setUpClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //AI code and optimization. i dont know it it really optimize something. quite laggy...
    private fun setUpDatePicker() {
        binding.chartDateFilterPicker.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            builder.setTitleText("Выберите временной диапазон:")

            val dateRangerPicker = builder.build()
            dateRangerPicker.show(requireActivity().supportFragmentManager, "date_range_picker")

//            val constraintBuilder = CalendarConstraints.Builder()
//                .build()
//            builder.setCalendarConstraints(constraintBuilder)
//            val dateRangerPicker = builder.build()
//            dateRangerPicker.show(requireActivity().supportFragmentManager, "date_range_picker")

//            dateRangerPicker.addOnPositiveButtonClickListener { selection ->
//                val startDate = selection.first
//                val endDate = selection.second
//                updateLabel(startDate, endDate)
//            }

            dateRangerPicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first ?: return@addOnPositiveButtonClickListener
                val endDate = selection.second ?: return@addOnPositiveButtonClickListener

                // Use a coroutine to handle date formatting off the main thread
                lifecycleScope.launch {
                    updateLabel(startDate, endDate)
                }
            }
        }
    }

    private suspend fun updateLabel(startDate: Long, endDate: Long) {
        withContext(Dispatchers.Default) {
            val startCalendar = Calendar.getInstance().apply { timeInMillis = startDate }
            val endCalendar = Calendar.getInstance().apply { timeInMillis = endDate }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedStartDate = dateFormat.format(startCalendar.time)
            val formattedEndDate = dateFormat.format(endCalendar.time)

            withContext(Dispatchers.Main) {
                binding.chartDateFilterPicker.setText("$formattedStartDate <-> $formattedEndDate")
            }
        }
    }

//    private fun updateLabel(startDate: Long, endDate: Long) {
//        val startCalendar = Calendar.getInstance().apply { timeInMillis = startDate }
//        val endCalendar = Calendar.getInstance().apply { timeInMillis = endDate }
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val formattedStartDate = dateFormat.format(startCalendar.time)
//        val formattedEndDate = dateFormat.format(endCalendar.time)
//        binding.chartDateFilterPicker.setText("$formattedStartDate <-> $formattedEndDate")
//    }


    private fun validate(): Boolean {
        //TODO data validation
        val dates = binding.chartDateFilterPicker.text
        val status = binding.chartStatusFilterSpinner.selectedItem.toString()
        val type = binding.chartTypeFilterSpinner.selectedItem.toString()
        val area = binding.chartAreaFilterSpinner.selectedItem.toString()
        return dates.contains(" <-> ") && status != "Выберите статус..." && type != "Выберите тип проблемы..." && area != "Выберите участок..."
    }

    private fun setUpSpinners() {
        val typeSpinner: Spinner = binding.chartTypeFilterSpinner
        val specializationAdapter =
            SpecializationAdapter(requireContext(), getSpecializationArray())
        typeSpinner.adapter = specializationAdapter

        val areaSpinner: Spinner = binding.chartAreaFilterSpinner
        val workplaceAdapter = WorkplaceAdapter(requireContext(), getWorkplaceArray())
        areaSpinner.adapter = workplaceAdapter

        val statusSpinner: Spinner = binding.chartStatusFilterSpinner
        val statusAdapter = StatusAdapter(requireContext(), getStatusArray())
        statusSpinner.adapter = statusAdapter
        //spinners should receive areas and specialisations list from backend. In ideal world
    }

    private fun setUpClickListeners() {
        binding.loadButton.setOnClickListener {
            if (binding.chartDateFilterPicker.text.isNotEmpty()) {
                val dates = binding.chartDateFilterPicker.text.split(" <-> ")
                val dateStart = dates[0]
                val dateEnd = dates[1]

                val status =
                    getStatusForCharts(binding.chartStatusFilterSpinner.selectedItem.toString())
                val spec = binding.chartTypeFilterSpinner.selectedItem as Specialization
                val type = spec.id
                val workplace = binding.chartAreaFilterSpinner.selectedItem as Workplace
                val area = workplace.id
                val request = BossRequest(dateStart, dateEnd, status, type, area)

                if (validate()) {
                    loadChart(request)
                } else {
                    showNotValidatedDialog()
                }
            } else {
                showNotValidatedDialog()
            }
        }
        binding.closeButton.setOnClickListener {
            binding.chart.clear()
            showFilter()
        }
    }

    private fun setUpObservers() {
        managerChartViewModel.chartData.observe(viewLifecycleOwner) { chartData: List<BarEntry> ->
            Log.i("chart data", chartData.toString())
            if(chartData.isEmpty()) {
                showPlug()
            } else {
                setUpChart(chartData)
                showContent()
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            filterLayout.isGone = true
            progressBar.isVisible = true
            chartLayout.isGone = true
            plug.isGone = true
            closeButton.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            filterLayout.isGone = true
            progressBar.isGone = true
            chartLayout.isVisible = true
            plug.isGone = true
            closeButton.isVisible = true
        }
    }

    private fun showFilter() {
        with(binding) {
            filterLayout.isVisible = true
            progressBar.isGone = true
            chartLayout.isGone = true
            plug.isGone = true
            closeButton.isGone = true
        }
    }

    private fun showPlug() {
        with(binding) {
            filterLayout.isGone = true
            progressBar.isGone = true
            chartLayout.isGone = true
            plug.isVisible = true
            closeButton.isVisible = true
        }
    }

    private fun loadChart(request: BossRequest?) {
        showLoading()
        lifecycleScope.launch {
            if (request != null) {
                managerChartViewModel.loadChartData(request)
            }
        }
    }

    private fun setUpChart(chartData: List<BarEntry>) {
        Log.i("set up", "set up")
        // Initialize the BarChart
        val barChart = binding.chart // Assuming you have a BarChart in your Fragment's layout

        // Create a BarDataSet from the chart data
        val barDataSet = BarDataSet(chartData, "График по датам").apply {
            color = resources.getColor(R.color.primary_color, null) // Set color for bars
            valueTextColor = resources.getColor(
                R.color.primary_color, null
            ) // Set color for value text
            valueTextSize = 12f // Set text size for values
        }

        // Create BarData object with the dataset
        val barData = BarData(barDataSet)

        // Set data to the chart
        barChart.data = barData

        // Customize chart appearance (optional)
        barChart.description.text = "График по датам" // Set chart description
        barChart.xAxis.labelRotationAngle = -45f // Rotate x-axis labels if needed
        barChart.xAxis.granularity = 1f // Set granularity to ensure one label per day

        // Refresh the chart
        barChart.invalidate() // Refreshes the chart to display updated data

        // Optionally enable touch gestures and scaling
        barChart.setTouchEnabled(true)
        barChart.isDragEnabled = true
        barChart.setScaleEnabled(true)
    }

    private fun showNotValidatedDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Неполные данные")
            setMessage("Пожалуйста, заполните все поля")
            setNegativeButton("Ок", null)
            show()
        }
    }
}

//TODO
// viewModel.load()
// converting data
// observers
// showing in chart