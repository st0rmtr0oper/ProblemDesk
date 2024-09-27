package com.example.problemdesk.presentation.manager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.problemdesk.R
import com.example.problemdesk.databinding.FragmentChartDetailsBottomSheetDialogBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.details.RequestorBottomSheetDialogFragment
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import com.example.problemdesk.presentation.general.getSpecialization
import com.example.problemdesk.presentation.general.getStatus
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChartDetailsBottomSheetDialogFragment(private val cards: List<Card>) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentChartDetailsBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var isShowingCharts: Boolean = true

    companion object {
        fun newInstance(cards: List<Card>) = ChartDetailsBottomSheetDialogFragment(cards)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartDetailsBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardsRv.adapter = CardRecyclerViewAdapter(::handleLogClick)
        setUpPieSpecChart(cards)
        setUpStatusPieChart(cards)
        setUpCards(cards)
        setUpClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpPieSpecChart(cards: List<Card>) {
        // Initialize the PieChart
        val pieChart = binding.specChart // Assuming you have a PieChart in your Fragment's layout

        // Count occurrences of each request_type
        val requestTypeCounts = mutableMapOf<Int, Int>()
        for (card in cards) {
            requestTypeCounts[card.requestType] =
                requestTypeCounts.getOrDefault(card.requestType, 0) + 1
        }

        // Prepare data entries for the PieChart
        val pieEntries = requestTypeCounts.map { (requestType, count) ->
            PieEntry(count.toFloat(), getSpecialization(requestType))
        }

        // Create a PieDataSet from the entries
        val pieDataSet = PieDataSet(pieEntries, "Специализации").apply {
            colors = listOf(
                resources.getColor(R.color.status_approved_color, null),
                resources.getColor(R.color.status_cancelled_color, null),
                resources.getColor(R.color.status_completed_color, null),
                resources.getColor(R.color.status_unchecked_color, null),
                resources.getColor(R.color.status_in_work_color, null),
                resources.getColor(R.color.chart_green, null),
                resources.getColor(R.color.chart_blue, null)
            )
//            valueTextColor = resources.getColor(R.color.primary_color, null)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_color)
            valueTextSize = 12f
        }

//        pieChart.legend.textColor = resources.getColor(R.color.primary_color, null)
        pieChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        pieChart.legend.isWordWrapEnabled = true


        // Create PieData object with the dataset
        val pieData = PieData(pieDataSet)

        // Set data to the chart
        pieChart.data = pieData

        // Customize chart appearance
        pieChart.description.text = ""

        // Enable hole in pie chart if desired
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        // Refresh the chart
        pieChart.invalidate() // Refreshes the chart to display updated data

        // Optionally enable touch gestures and scaling
        pieChart.setTouchEnabled(true)

    }

    private fun setUpStatusPieChart(cards: List<Card>) {
        // Initialize the PieChart
        val statusPieChart =
            binding.statusChart // Assuming you have a PieChart in your Fragment's layout

        // Count occurrences of each status_id
        val statusCounts = mutableMapOf<Int, Int>()
        for (card in cards) {
            statusCounts[card.statusId] = statusCounts.getOrDefault(card.statusId, 0) + 1
        }

        // Prepare data entries for the PieChart
        val pieEntries = statusCounts.map { (statusId, count) ->
            PieEntry(count.toFloat(), getStatus(statusId))
        }

        // Create a PieDataSet from the entries
        val pieDataSet = PieDataSet(pieEntries, "Статусы").apply {
            colors = listOf(
                resources.getColor(R.color.status_approved_color, null),
                resources.getColor(R.color.status_cancelled_color, null),
                resources.getColor(R.color.status_completed_color, null),
                resources.getColor(R.color.status_unchecked_color, null),
                resources.getColor(R.color.status_in_work_color, null),
                resources.getColor(R.color.chart_green, null),
                resources.getColor(R.color.chart_blue, null)
            )
//            valueTextColor = resources.getColor(R.color.primary_color, null)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_color)
            valueTextSize = 12f
        }

        statusPieChart.legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_color)
        statusPieChart.legend.isWordWrapEnabled = true

        // Create PieData object with the dataset
        val pieData = PieData(pieDataSet)

        // Set data to the chart
        statusPieChart.data = pieData

        // Customize chart appearance
        statusPieChart.description.text = ""

        // Enable hole in pie chart if desired
        statusPieChart.isDrawHoleEnabled = true
        statusPieChart.setHoleColor(Color.WHITE)

        // Refresh the chart
        statusPieChart.invalidate() // Refreshes the chart to display updated data

        // Optionally enable touch gestures and scaling
        statusPieChart.setTouchEnabled(true)
    }

    private fun setUpClickListeners() {
        binding.showButton.setOnClickListener {
            if (isShowingCharts) {
                hideChartsShowCards()
                isShowingCharts = false
                binding.showButton.text = "Посмотреть графики"
            } else {
                hideCardsShowCharts()
                isShowingCharts = true
                binding.showButton.text = "Посмотреть заявки"
            }
        }
    }

    private fun hideChartsShowCards() {
        with(binding) {
            cardsRv.isVisible = true
            statusChart.isGone = true
            specChart.isGone = true
        }
    }

    private fun hideCardsShowCharts() {
        with(binding) {
            cardsRv.isGone = true
            statusChart.isVisible = true
            specChart.isVisible = true
        }
    }

    private fun handleLogClick(card: Card) {
        //TODO i dont need it
        showBottomSheetDialogFragmentRequestor(
            card.requestId,
            card.statusId,
            card.createdAt,
            card.requestType.toString(),
            card.areaId.toString(),
            card.description
        )
    }

    private fun showBottomSheetDialogFragmentRequestor(
        requestId: Int,
        stat: Int,
        date: String,
        spec: String,
        area: String,
        desc: String
    ) {
        val role = "manager"
        val requestorBottomSheetDialogFragment =
            RequestorBottomSheetDialogFragment(requestId, stat, role, date, spec, area, desc)
        requestorBottomSheetDialogFragment.show(
            parentFragmentManager,
            RequestorBottomSheetDialogFragment::class.java.simpleName
        )
    }

    private fun setUpCards(cards: List<Card>) {
        (binding.cardsRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
    }
}