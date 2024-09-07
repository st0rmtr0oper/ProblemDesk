package com.example.problemdesk.presentation.mytasks.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.models.TaskManipulationResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class NewTasksViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    private val _takeSuccess = MutableLiveData<Boolean>()
    val takeSuccess: LiveData<Boolean> get() = _takeSuccess

    fun loadCards(userId: Int) {
        val repository = DeskRepositoryImplementation()
        var response: List<Card>

        viewModelScope.launch {
            try {
                response = repository.getExecutorUnassigned(userId)
                Log.i("!--{{{UNASSIGNED}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{UNASSIGNED}}}--!", e.toString())
            }
        }
    }

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
}