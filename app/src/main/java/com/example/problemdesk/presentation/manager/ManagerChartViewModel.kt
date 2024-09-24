package com.example.problemdesk.presentation.manager

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.BossRequest
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.Card
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ManagerChartViewModel(private val application: Application) : AndroidViewModel(application) {
    //TODO implement
    private val _chartData = MutableLiveData<List<BarEntry>>()
    val chartData: LiveData<List<BarEntry>> get() = _chartData

    fun loadChartData(
        request: BossRequest
    ) {
        val repository = DeskRepositoryImpl(application)
        var response: List<Card>
        viewModelScope.launch {
            try {
                response = repository.bossRequests(
                    request.fromDate,
                    request.untilDate,
                    request.status,
                    request.requestType,
                    request.areaId
                )
                _chartData.postValue(convertChartData(response))
            } catch (e: Exception) {
            }
        }
    }

    private fun convertChartData(rawData: List<Card>): List<BarEntry> {
        // Map to hold counts of cards by date
        val dateCountMap = mutableMapOf<String, Int>()
        // Define a date format that matches your created_at string format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (card in rawData) {
            // Parse the created_at date string into a Date object
            val date = card.createdAt.substring(0, 10) // Assuming createdAt is in "yyyy-MM-dd HH:mm:ss" formats
            // Increment the count for this date
            dateCountMap[date] = dateCountMap.getOrDefault(date, 0) + 1
        }
        // Prepare the list of BarEntry for the chart
        val barEntries = mutableListOf<BarEntry>()

        // Convert map entries to BarEntry list
        dateCountMap.forEach { (date, count) ->
            // Convert date string to a float value representing the x-axis position (e.g., index)
            val xValue = dateCountMap.keys.indexOf(date).toFloat()
            barEntries.add(BarEntry(xValue, count.toFloat()))
        }
        // Optionally sort barEntries by date if needed
        barEntries.sortBy { it.x }

        return barEntries.toList()
    }
}