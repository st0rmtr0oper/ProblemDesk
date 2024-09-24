package com.example.problemdesk.presentation.manager

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.BossRequest
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class ManagerChartViewModel(private val application: Application) : ViewModel() {
    //TODO implement
    private val _chartData = MutableLiveData<List<Card>>()
    val chartData: LiveData<List<Card>> get() = _chartData
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

    private fun convertChartData(rawData: List<Card>): List<Card> {
//        val chartData: ChartData<>
        //TODO implement converting
        return rawData
    }
}