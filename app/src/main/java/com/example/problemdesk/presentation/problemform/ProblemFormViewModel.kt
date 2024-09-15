package com.example.problemdesk.presentation.problemform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.CreateRequestRequest
import com.example.problemdesk.data.models.CreateRequestResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.presentation.general.SingleLiveEvent
import kotlinx.coroutines.launch

class ProblemFormViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _successStatus = MutableLiveData<SingleLiveEvent<Boolean>>()
    val successStatus: LiveData<SingleLiveEvent<Boolean>> get() = _successStatus

    private val _errorStatus = MutableLiveData<SingleLiveEvent<String>>()
    val errorStatus: LiveData<SingleLiveEvent<String>> get() = _errorStatus

    suspend fun createRequest(request: CreateRequestRequest) {
        val repository = DeskRepositoryImpl(application)
        var createRequestResponse: CreateRequestResponse

        //TODO test with 0 area (invalid input)

        viewModelScope.launch {
            try {
                createRequestResponse = repository.createRequest(request)
                _successStatus.postValue(SingleLiveEvent(createRequestResponse.message == "Request created successfully"))
            } catch (e: Exception) {
                _successStatus.postValue(SingleLiveEvent(false))
                _errorStatus.postValue(SingleLiveEvent(e.toString()))
            }
        }
    }
}