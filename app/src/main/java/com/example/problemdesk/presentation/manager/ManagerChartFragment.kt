package com.example.problemdesk.presentation.manager

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

//TODO loading doesnt work

class ManagerChartFragment : Fragment() {

    private var _binding: FragmentManagerChartBinding? = null
    private val binding get() = _binding!!

    private val managerChartViewModel: ManagerChartViewModel by lazy {
        ViewModelProvider(this)[ManagerChartViewModel::class.java]
    }

    private val cards: MutableList<Card> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerChartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStatChart()
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
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedStartDate = dateFormat.format(startCalendar.time)
            val formattedEndDate = dateFormat.format(endCalendar.time)

            withContext(Dispatchers.Main) {
                binding.chartDateFilterPicker.setText("$formattedStartDate <-> $formattedEndDate")
            }
        }
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
            var dateStart: String? = null
            var dateEnd: String? = null
            var status: String? = null
            var type: Int? = null
            var area: Int? = null

            if (binding.chartDateFilterPicker.text.isNotEmpty()) {
                val dates = binding.chartDateFilterPicker.text.split(" <-> ")
                dateStart = dates[0]
                dateEnd = dates[1]
            }
            if (getStatusForCharts(binding.chartStatusFilterSpinner.selectedItem.toString()) != "status error") {
                status =
                    getStatusForCharts(binding.chartStatusFilterSpinner.selectedItem.toString())
            }
            val spec = binding.chartTypeFilterSpinner.selectedItem as Specialization
            if (spec.id != 0) {
                type = spec.id
            }
            val workplace = binding.chartAreaFilterSpinner.selectedItem as Workplace
            if (workplace.id != 0) {
                area = workplace.id
            }
            val request = BossRequest(dateStart, dateEnd, status, type, area)
            loadChart(request)
        }
        binding.detailsButton.setOnClickListener {
            showBottomSheetDialogFragmentDetails()
        }
        binding.dropButton.setOnClickListener {
            dropFilters()
        }
    }

    private fun setUpObservers() {
        managerChartViewModel.chartData.observe(viewLifecycleOwner) { chartData: Pair<List<BarEntry>, List<String>> ->
            //TODO костыльное решение
            if (chartData.first.isEmpty()) {
                showPlug()
            } else {
                setUpChart(chartData.first, chartData.second)
                showContent()
            }
        }
        managerChartViewModel.cards.observe(viewLifecycleOwner) { newCards: List<Card> ->
            Log.i("newCards", newCards.count().toString())
            cards.clear()
            cards.addAll(newCards)
            binding.detailsButton.isVisible = true
        }
    }

    private fun showLoading() {
        Log.i("loading", "loading")
        with(binding) {
            progressBar.isVisible = true
            chartLayout.isGone = true
            plug.isGone = true
        }
    }

    private fun showContent() {
        Log.i("content", "content")
        with(binding) {
            progressBar.isGone = true
            chartLayout.isVisible = true
            plug.isGone = true
//            detailsButton.isVisible = true
        }
    }

    private fun showPlug() {
        with(binding) {
            progressBar.isGone = true
            chartLayout.isGone = true
            plug.isVisible = true
            detailsButton.isGone = true
        }
    }

    private fun loadStatChart() {
        showLoading()
        cards.clear()
        lifecycleScope.launch {
            managerChartViewModel.loadStatChartData()
            binding.detailsButton.isGone = true
        }
    }

    private fun loadChart(request: BossRequest?) {
        binding.chart.clear()
        showLoading()
        lifecycleScope.launch {
            if (request != null) {
                managerChartViewModel.loadChartData(request)
            }
        }
    }

    class CustomDateFormatter(private val labels: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            // Ensure the index is within bounds of labels
            val index = value.toInt()
            //TODO пиздец костыли
            if (index >= 0 && index < labels.size && labels[0].substring(2,3) != "-") {
                Log . i ("1", labels[index])
                return formatCustomDate(labels[index])
            } else if (index >= 0 && index < labels.size && labels[0].substring(2,3) == "-") {
                return formatStatCustomDate(labels[index])
            } else {
                return ""
            }
        }

        private fun formatCustomDate(string: String): String {
            Log.i("2", string.substring(8, 10))
            return string.substring(8, 10) + " " + giveMonth(string.substring(5, 7))
        }

        private fun formatStatCustomDate(string: String): String {
            Log.i("2", string.substring(8, 10))
            return string.substring(0, 2) + " " + giveMonth(string.substring(3, 5))
        }

        private fun giveMonth(string: String): String {
            Log.i("3", string)
            return when (string) {
                "01" -> "янв."
                "02" -> "фев."
                "03" -> "мар."
                "04" -> "апр."
                "05" -> "мая"
                "06" -> "июн."
                "07" -> "июл."
                "08" -> "авг."
                "09" -> "сен."
                "10" -> "окт."
                "11" -> "ноя."
                "12" -> "дек."
                else -> "date error"
            }
        }
    }

    private fun setUpChart(chartData: List<BarEntry>, labels: List<String>) {
        // Initialize the BarChart
        val barChart = binding.chart // Assuming you have a BarChart in your Fragment's layout

        // Create a BarDataSet from the chart data
        val barDataSet = BarDataSet(chartData, "График по датам").apply {
            color = resources.getColor(R.color.primary_color, null) // Set color for bars
            valueTextColor =
                    ContextCompat.getColor(requireContext(), R.color.text_color)
//                resources.getColor(R.color.primary_color, null) // Set color for value text
            valueTextSize = 12f // Set text size for values
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString() // Convert float values to integers
                }
            }
        }


        // Create BarData object with the dataset
        val barData = BarData(barDataSet)

        // Set data to the chart
        barChart.data = barData

        // Customize chart appearance
        barChart.description.text = "" // Set chart description

        // Set up x-axis labels
        val xAxis = barChart.xAxis
        xAxis.labelRotationAngle = -45f // Rotate x-axis labels if needed
        xAxis.granularity = 1f // Set granularity to ensure one label per value
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.textColor = resources.getColor(R.color.text_color, null)
//        barChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)


        // Use the custom formatter to display dates, weeks, or months
        xAxis.valueFormatter = CustomDateFormatter(labels)
        // Optionally, apply the same to the right axis if it's enabled
        barChart.axisRight.isEnabled = false // If you don't want the right axis

        xAxis.yOffset = 0f

        // Set up y-axis to display integer values only
        val leftAxis = barChart.axisLeft
        leftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString() // Display y-axis values as integers
            }
        }
        // Optionally, apply the same to the right axis if it's enabled
        barChart.axisRight.isEnabled = false // If you don't want the right axis


        // Refresh the chart
        barChart.invalidate() // Refreshes the chart to display updated data

        // Optionally enable touch gestures and scaling
        barChart.setTouchEnabled(true)
        barChart.isDragEnabled = true
        barChart.setScaleEnabled(true)

        barChart.isDoubleTapToZoomEnabled = true
        barChart.setPinchZoom(true)
    }

    private fun showBottomSheetDialogFragmentDetails() {
        val chartDetailsBottomSheetDialogFragment =
            ChartDetailsBottomSheetDialogFragment.newInstance(cards)
        Log.i("cards", cards.count().toString())
        chartDetailsBottomSheetDialogFragment.show(
            parentFragmentManager,
            ChartDetailsBottomSheetDialogFragment::class.java.simpleName
        )
    }

    private fun dropFilters() {
        with(binding) {
            chartDateFilterPicker.text.clear()
            chartAreaFilterSpinner.setSelection(0)
            chartTypeFilterSpinner.setSelection(0)
            chartStatusFilterSpinner.setSelection(0)
        }
        loadStatChart()
    }
}