package com.example.problemdesk.presentation.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.presentation.general.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RequestorBottomSheetDialogViewModel(private val application: Application) : AndroidViewModel(application) {


    //TODO check ListAdapter
    //logs

    private val _logs = MutableLiveData<List<RequestLog>>()
    val logs: LiveData<List<RequestLog>> get() = _logs

    fun loadHistory(requestId: Int) {
        val repository = DeskRepositoryImpl(application)
        var response: List<RequestLog>

        viewModelScope.launch {
            try {
                response = repository.requestHistory(requestId)
//                Log.i("!--{{{REQUEST HISTORY}}}--!", response.toString())
                _logs.postValue(response)
            } catch (e: Exception) {
//                Log.i("!--{{{REQUEST HISTORY}}}--!", e.toString())
            }
        }
    }

    //master

    //TODO idk how good this shit is

    //TODO done !

    private val _approveSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val approveSuccess: LiveData<SingleLiveEvent<String>> get() = _approveSuccess

    fun masterApprove(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterApprove(request)
//                Log.i("!--{{{MASTER APPROVE}}}-!", response.toString())
                if (response.message == "Request approved successfully") {
                    _approveSuccess.postValue(SingleLiveEvent("success"))
                }
            } catch (e: Exception) {
                _approveSuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{MASTER APPROVE}}}-!", e.toString())
            }
        }
    }

    //TODO done ?

    private val _denySuccess = MutableLiveData<SingleLiveEvent<String>>()
    val denySuccess: LiveData<SingleLiveEvent<String>> get() = _denySuccess

    fun masterDeny(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterDeny(request)
//                Log.i("!--{{{MASTER DENY}}}-!", response.toString())
                if (response.message == "Request denied successfully") {
                    _denySuccess.postValue(SingleLiveEvent("success"))
                }
            } catch (e: Exception) {
                _denySuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{MASTER DENY}}}-!", e.toString())
            }
        }
    }

    //executor

    //TODO done !

    private val _takeSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val takeSuccess: LiveData<SingleLiveEvent<String>> get() = _takeSuccess

    fun takeTask(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        viewModelScope.launch {
            try {
                response = repository.takeOnWork(request)
                if (response.message == "Request accepted into work successfully") {
                    _takeSuccess.postValue(SingleLiveEvent("success"))
                }
//                Log.i("!--{{{TAKE ON WORK}}}--!", response.toString())
            } catch (e: Exception) {
                _takeSuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{TAKE ON WORK}}}--!", e.toString())
            }
        }
    }

    //TODO done !

    private val _cancelSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val cancelSuccess: LiveData<SingleLiveEvent<String>> get() = _cancelSuccess

    fun executorCancel(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.executorCancel(request)
//                Log.i("!--{{{EXECUTOR CANCEL}}}-!", response.toString())
                if (response.message == "Request cancelled successfully") {
                    _cancelSuccess.postValue(SingleLiveEvent("success"))
                }
            } catch (e: Exception) {
                _cancelSuccess.postValue(SingleLiveEvent("success"))
//                Log.i("!--{{{EXECUTOR CANCEL}}}-!", e.toString())
            }
        }
    }

    //TODO done !

    private val _completeSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val completeSuccess: LiveData<SingleLiveEvent<String>> get() = _completeSuccess

    fun executorComplete(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.executorComplete(request)
                if (response.message == "Request completed successfully") {
                    _completeSuccess.postValue(SingleLiveEvent("success"))
                }
//                Log.i("!--{{{EXECUTOR COMPLETE}}}-!", response.toString())
//                _denySuccess.postValue(response.message == "Request completed successfully")
            } catch (e: Exception) {
                _completeSuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{EXECUTOR COMPLETE}}}-!", e.toString())
            }
        }
    }

    //requestor

    //TODO done !

    private val _reqConfirmSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val reqConfirmSuccess: LiveData<SingleLiveEvent<String>> get() = _reqConfirmSuccess

    fun requestorConfirm(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.requestorConfirm(request)
                if (response.message == "Request confirmed successfully") {
                    _reqConfirmSuccess.postValue(SingleLiveEvent("success"))
                }
//                Log.i("!--{{{REQUESTOR CONFIRM}}}-!", response.toString())
//                _denySuccess.postValue(response.message == "Request confirmed successfully")
            } catch (e: Exception) {
                _reqConfirmSuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{REQUESTOR CONFIRM}}}-!", e.toString())
            }
        }
    }

    //TODO done !

    private val _reqDenySuccess = MutableLiveData<SingleLiveEvent<String>>()
    val reqDenySuccess: LiveData<SingleLiveEvent<String>> get() = _reqDenySuccess

    fun requestorDeny(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.requestorDeny(request)
                if (response.message == "Request denied successfully") {
                    _reqDenySuccess.postValue(SingleLiveEvent("success"))
                }
//                Log.i("!--{{{REQUESTOR DENY}}}-!", response.toString())
            } catch (e: Exception) {
                _reqDenySuccess.postValue(SingleLiveEvent(e.toString()))
//                Log.i("!--{{{REQUESTOR DENY}}}-!", e.toString())
            }
        }
    }

    private val _reqDeleteSuccess = MutableLiveData<SingleLiveEvent<String>>()
    val reqDeleteSuccess: LiveData<SingleLiveEvent<String>> get() = _reqDeleteSuccess

    fun requestorDelete(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImpl(application)
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.requestorDelete(request)
                if (response.message == "Request marked as deleted successfully") {
                    _reqDeleteSuccess.postValue(SingleLiveEvent("success"))
                }
                Log.i("!--{{{REQUESTOR DELETE}}}-!", response.toString())
            } catch (e: Exception) {
                _reqDeleteSuccess.postValue(SingleLiveEvent(e.toString()))
                Log.i("!--{{{REQUESTOR DELETE}}}-!", e.toString())
            }
        }
    }
}