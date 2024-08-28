package com.example.problemdesk.presentation.mastermenu.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MasterApproveViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    private val _approveSuccess = MutableLiveData<Boolean>()
    val approveSuccess: LiveData<Boolean> get() = _approveSuccess

    private val _denySuccess = MutableLiveData<Boolean>()
    val denySuccess: LiveData<Boolean> get() = _denySuccess

    fun loadCards(userId: Int) {
        val repository = DeskRepositoryImplementation()
        var response: List<Card>

        viewModelScope.launch {
            try {
                response = repository.getUnderMasterApproval(userId)
                Log.i("!--{{{UNAPPROVED}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{UNAPPROVED}}}--!", e.toString())
            }
        }
    }

    fun masterApprove(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImplementation()
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterApprove(request)
                Log.i("!--{{{MASTER APPROVE}}}-!", response.toString())
                _approveSuccess.postValue(response.message == "Request accepted into work successfully")

            } catch (e: Exception) {
                Log.i("!--{{{MASTER APPROVE}}}-!", e.toString())
            }
        }
    }

    fun masterDeny(request: TaskManipulationRequest) {
        val repository = DeskRepositoryImplementation()
        var response: TaskManipulationResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = repository.masterDeny(request)
                Log.i("!--{{{MASTER DENY}}}-!", response.toString())
                //TODO check response
                _denySuccess.postValue(response.message == "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

            } catch (e: Exception) {
                Log.i("!--{{{MASTER DENY}}}-!", e.toString())
            }
        }
    }
}