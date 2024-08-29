package com.example.problemdesk.presentation.logs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import com.example.problemdesk.domain.models.RequestLog
import kotlinx.coroutines.launch


class LogsViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<RequestLog>>()
    val cards: LiveData<List<RequestLog>> get() = _cards

    fun loadLogs(requestId: Int) {
        val repository = DeskRepositoryImplementation()
        var response: List<RequestLog>

        viewModelScope.launch {
            try {
                response = repository.requestHistory(requestId)
                Log.i("!--{{{LOGS}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{LOGS}}}--!", e.toString())
            }
        }
    }
}