package com.example.problemdesk.presentation.problemform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.firebase.ChatState
import com.example.problemdesk.data.firebase.SendMessageDTO
import kotlinx.coroutines.launch

class ProblemFormViewModel : ViewModel() {

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