package com.example.problemdesk.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import com.example.problemdesk.domain.models.RequestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RequestorBottomSheetDialogViewModel : ViewModel() {
    private val _logs = MutableLiveData<List<RequestLog>>()
    val logs: LiveData<List<RequestLog>> get() = _logs

    fun loadHistory(requestId: Int) {
        val repository = DeskRepositoryImplementation()
        var response: List<RequestLog>

        viewModelScope.launch {
            try {
                response = repository.requestHistory(requestId)
                Log.i("!--{{{REQUEST HISTORY}}}--!", response.toString())
                _logs.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{REQUEST HISTORY}}}--!", e.toString())
            }
        }
    }

    private val _approveSuccess = MutableLiveData<Boolean>()
    val approveSuccess: LiveData<Boolean> get() = _approveSuccess

    fun masterApprove(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImplementation()
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterApprove(request)
                Log.i("!--{{{MASTER APPROVE}}}-!", response.toString())
                _approveSuccess.postValue(response.message == "Request approved successfully")
            } catch (e: Exception) {
                Log.i("!--{{{MASTER APPROVE}}}-!", e.toString())
            }
        }
    }

    private val _denySuccess = MutableLiveData<Boolean>()
    val denySuccess: LiveData<Boolean> get() = _denySuccess

    fun masterDeny(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImplementation()
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterDeny(request)
                Log.i("!--{{{MASTER DENY}}}-!", response.toString())
                //TODO check response
                _denySuccess.postValue(response.message == "Request denied successfully")
            } catch (e: Exception) {
                Log.i("!--{{{MASTER DENY}}}-!", e.toString())
            }
        }
    }

    private val _takeSuccess = MutableLiveData<Boolean>()
    val takeSuccess: LiveData<Boolean> get() = _takeSuccess

    fun takeTask(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImplementation()
        var response: TaskManipulationResponse

        viewModelScope.launch {
            try {
                response = repository.takeOnWork(request)
                Log.i("!--{{{TAKE ON WORK}}}--!", response.toString())
                _takeSuccess.postValue(response.message == "Request accepted into work successfully")
            } catch (e: Exception) {
                Log.i("!--{{{TAKE ON WORK}}}--!", e.toString())
            }
        }
    }

    private val _cancelSuccess = MutableLiveData<Boolean>()
    val cancelSuccess: LiveData<Boolean> get() = _cancelSuccess

    fun executorCancel(request: TaskManipulationRequest) {
            val repository = DeskRepositoryImplementation()
            var response: TaskManipulationResponse

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    response = repository.executorCancel(request)
                    Log.i("!--{{{EXECUTOR CANCEL}}}-!", response.toString())
                    //TODO check response
                    _cancelSuccess.postValue(response.message == "Request canceled successfully")
                } catch (e: Exception) {
                    Log.i("!--{{{EXECUTOR CANCEL}}}-!", e.toString())
            }
        }
    }

    private val _completeSuccess = MutableLiveData<Boolean>()
    val completeSuccess: LiveData<Boolean> get() = _completeSuccess

    fun executorComplete(request: TaskManipulationRequest) {
            val repository = DeskRepositoryImplementation()
            var response: TaskManipulationResponse

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    response = repository.executorComplete(request)
                    Log.i("!--{{{EXECUTOR COMPLETE}}}-!", response.toString())
                    //TODO check response
                    _denySuccess.postValue(response.message == "Request completed successfully")

                } catch (e: Exception) {
                    Log.i("!--{{{EXECUTOR COMPLETE}}}-!", e.toString())
            }
        }
    }

    private val _reqConfirmSuccess = MutableLiveData<Boolean>()
    val reqCancelSuccess: LiveData<Boolean> get() = _reqConfirmSuccess

    fun requestorConfirm(request: TaskManipulationRequest) {
            val repository = DeskRepositoryImplementation()
            var response: TaskManipulationResponse

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    response = repository.requestorConfirm(request)
                    Log.i("!--{{{REQUESTOR CONFIRM}}}-!", response.toString())
                    //TODO check response
                    _denySuccess.postValue(response.message == "Request confirmed successfully")

                } catch (e: Exception) {
                    Log.i("!--{{{REQUESTOR CONFIRM}}}-!", e.toString())
            }
        }
    }

    private val _reqDenySuccess = MutableLiveData<Boolean>()
    val reqDenySuccess: LiveData<Boolean> get() = _reqDenySuccess

    fun requestorDeny(request: TaskManipulationRequest) {
            val repository = DeskRepositoryImplementation()
            var response: TaskManipulationResponse

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    response = repository.requestorDeny(request)
                    Log.i("!--{{{REQUESTOR DENY}}}-!", response.toString())
                    //TODO check response
                    _denySuccess.postValue(response.message == "Request denied successfully")
                } catch (e: Exception) {
                    Log.i("!--{{{REQUESTOR DENY}}}-!", e.toString())
            }
        }
    }

    private val _reqDeleteSuccess = MutableLiveData<Boolean>()
    val reqDeleteSuccess: LiveData<Boolean> get() = _reqDeleteSuccess

    fun requestorDelete(request: TaskManipulationRequest) {
            val repository = DeskRepositoryImplementation()
            var response: TaskManipulationResponse

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    response = repository.requestorDelete(request)
                    Log.i("!--{{{REQUESTOR DELETE}}}-!", response.toString())
                    _denySuccess.postValue(response.message == "Request marked as deleted successfully")
                } catch (e: Exception) {
                    Log.i("!--{{{REQUESTOR DELETE}}}-!", e.toString())

            }
        }
    }
}