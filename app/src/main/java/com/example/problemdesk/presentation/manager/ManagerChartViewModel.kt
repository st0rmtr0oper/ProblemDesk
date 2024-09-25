package com.example.problemdesk.presentation.manager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.BossRequest
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.MockStat
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ManagerChartViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _chartData = MutableLiveData<Pair<List<BarEntry>, List<String>>>()
    val chartData: LiveData<Pair<List<BarEntry>, List<String>>> get() = _chartData

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
                if (response.isEmpty()) {
                    val emptyList1 = emptyList<BarEntry>()
                    val emptyList2 = emptyList<String>()
                    val pair = Pair(emptyList1, emptyList2)
                    _chartData.postValue(pair)
                } else {
                    _chartData.postValue(convertChartData(response))
                }
            } catch (e: Exception) {
            }
        }
    }

    fun loadMockChartData() {
        val repository = DeskRepositoryImpl(application)
        var response: List<MockStat>
        viewModelScope.launch {
            try {
                response = repository.getAllStats()
                val barEntries = convertMockStatToBarEntries(response)
                val pair = Pair(barEntries, response.map { it.date })
                _chartData.postValue(pair)
            } catch (e: Exception) {
            }
        }
    }

    private fun convertMockStatToBarEntries(mockStats: List<MockStat>): List<BarEntry> {
        // Assuming the date in MockStat is in "yyyy-MM-dd" format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Parse the dates and create BarEntries
        val barEntries = mutableListOf<BarEntry>()

        mockStats.forEachIndexed { index, mockStat ->
            val date = dateFormat.parse(mockStat.date)!!
            val xValue = index.toFloat() // Use the index as the x-axis value
            val yValue = mockStat.events.toFloat() // The number of events as the y-axis value
            barEntries.add(BarEntry(xValue, yValue))
        }

        return barEntries
    }


    //TODO AI generated
    private fun convertChartData(rawData: List<Card>): Pair<List<BarEntry>, List<String>> {
        val dateCountMap = mutableMapOf<String, Int>()

        // Step 1: Collect card data into dateCountMap (same as before)
        for (card in rawData) {
            val date: String
            if (card.updatedAt.isNullOrEmpty()) {
                date = card.createdAt.substring(0, 10) // Extract the date part
            } else {
                date = card.updatedAt.substring(0, 10)
            }
            dateCountMap[date] = dateCountMap.getOrDefault(date, 0) + 1
        }

        // Step 2: Convert the dates to a sorted list and determine the range of days
        val sortedDates = dateCountMap.keys.sorted()
        val firstDate = sortedDates.first()
        val lastDate = sortedDates.last()
        val daysRange = calculateDaysRange(firstDate, lastDate)

        val barEntries = mutableListOf<BarEntry>()

        when {
            // 1-7 days: Show data by day
            daysRange <= 7 -> {
                sortedDates.forEachIndexed { index, date ->
                    val count = dateCountMap[date]?.toFloat() ?: 0f
                    barEntries.add(BarEntry(index.toFloat(), count))
                }
            }

            // 8-31 days: Show data grouped by week
            daysRange in 8..31 -> {
                val weekCountMap = mutableMapOf<Int, Int>()
                sortedDates.forEach { date ->
                    val weekOfYear = getWeekOfYear(date)
                    weekCountMap[weekOfYear] =
                        weekCountMap.getOrDefault(weekOfYear, 0) + dateCountMap[date]!!
                }
                weekCountMap.forEach { (week, count) ->
                    barEntries.add(BarEntry(week.toFloat(), count.toFloat()))
                }
            }

            // 32+ days: Show data grouped by month
            daysRange > 31 -> {
                val monthCountMap = mutableMapOf<String, Int>()
                sortedDates.forEach { date ->
                    val month = getYearMonth(date)
                    monthCountMap[month] =
                        monthCountMap.getOrDefault(month, 0) + dateCountMap[date]!!
                }
                monthCountMap.toList().forEachIndexed { index, (month, count) ->
                    barEntries.add(BarEntry(index.toFloat(), count.toFloat()))
                }
            }
        }

        barEntries.sortBy { it.x }


        //TODO labels

        val labels: List<String> =
            generateLabelsForDataRange(rawData) // This function generates day/week/month labels
//        Pair<barEntries, labels>
        val pair = Pair(barEntries, labels)
        return pair
    }

    private fun generateLabelsForDataRange(rawData: List<Card>): List<String> {
        // Extract the dates from rawData
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Map to store dates either from updatedAt or createdAt
        val dates = rawData.map { card ->
            val dateStr = if (card.updatedAt.isNullOrEmpty()) card.createdAt else card.updatedAt
            dateFormat.parse(dateStr.substring(0, 10))!!
        }.sorted()

        // Check the number of days between the first and last date
        val firstDate = dates.first()
        val lastDate = dates.last()
        val diffInDays = ((lastDate.time - firstDate.time) / (1000 * 60 * 60 * 24)).toInt() + 1

        return when {
            diffInDays <= 7 -> {
                // Return each day as a label
                dates.map { dateFormat.format(it) }
            }

            diffInDays in 8..31 -> {
                // Group by week and return week-based labels
                generateWeeklyLabels(firstDate, lastDate)
            }

            else -> {
                // Group by month and return month-based labels
                generateMonthlyLabels(firstDate, lastDate)
            }
        }
    }

    private fun generateWeeklyLabels(startDate: Date, endDate: Date): List<String> {
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val weeklyLabels = mutableListOf<String>()
        var weekCounter = 1

        while (calendar.time.before(endDate) || calendar.time == endDate) {
            val weekLabel = "Неделя $weekCounter"
            weeklyLabels.add(weekLabel)

            // Move to the next week
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
            weekCounter++
        }

        return weeklyLabels
    }

    private fun generateMonthlyLabels(startDate: Date, endDate: Date): List<String> {
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        val monthlyLabels = mutableListOf<String>()

        while (calendar.time.before(endDate) || calendar.time == endDate) {
            val monthLabel = SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(calendar.time)
            monthlyLabels.add(monthLabel)

            // Move to the next month
            calendar.add(Calendar.MONTH, 1)
        }

        return monthlyLabels
    }


    private fun calculateDaysRange(startDate: String, endDate: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = dateFormat.parse(startDate) ?: return 0
        val end = dateFormat.parse(endDate) ?: return 0
        val difference = end.time - start.time
        return (difference / (1000 * 60 * 60 * 24)).toInt() // Convert milliseconds to days
    }

    private fun getWeekOfYear(date: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(date)!!
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    private fun getYearMonth(date: String): String {
        return date.substring(0, 7) // Returns "yyyy-MM"
    }


//    private fun convertChartData(rawData: List<Card>): List<BarEntry> {
//        // Map to hold counts of cards by date
//
//
//        val dateCountMap = mutableMapOf<String, Int>()
//        // Define a date format that matches your created_at string format
////        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        for (card in rawData) {
//            Log.i("asdadsa", card.toString())
//            // Parse the created_at date string into a Date object
//            val date: String
//            if (card.updatedAt.isNullOrEmpty()) {
//                date = card.createdAt.substring(0, 10) // Assuming createdAt is in "yyyy-MM-dd HH:mm:ss" formats
//            } else {
//                date = card.updatedAt.substring(0, 10)
//            }
//            Log.i("date", date)
//            // Increment the count for this date
//            dateCountMap[date] = dateCountMap.getOrDefault(date, 0) + 1
//        }
//        // Prepare the list of BarEntry for the chart
//        val barEntries = mutableListOf<BarEntry>()
//
//        // Convert map entries to BarEntry list
//        dateCountMap.forEach { (date, count) ->
//            // Convert date string to a float value representing the x-axis position (e.g., index)
//            val xValue = dateCountMap.keys.indexOf(date).toFloat()
//            barEntries.add(BarEntry(xValue, count.toFloat()))
//        }
//        // Optionally sort barEntries by date if needed
//        barEntries.sortBy { it.x }
//
//        return barEntries.toList()
//    }
}