package com.example.problemdesk.presentation.problemform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.launch

class ProblemFormViewModel : ViewModel() {

    suspend fun createRequest(requestType: Int, userId: Int, areaId: Int, description: String) {
        val repository = DeskRepositoryImplementation()
        viewModelScope.launch {
            repository.createRequest(requestType, userId, areaId, description)
        }
        return
    }
//    var state by mutableStateOf()
//    (ChatState()) private set

//    fun sendMessage(isBroadcast: Boolean) {
//        viewModelScope.launch {
//            val messageDTO = SendMessageDTO(
//                to = if(isBroadcast) null else state.remoteToken
//            )
//        }
//    }
}