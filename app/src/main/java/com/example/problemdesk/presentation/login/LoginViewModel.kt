package com.example.problemdesk.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.data.repository.DeskRepositoryImplementation

class LoginViewModel : ViewModel() {

//    private val _userId = MutableLiveData<Int>()
//    val userId: LiveData<Int> get() = _userId

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> get() = _userRole

    suspend fun validate(login: String, password: String  /*, resources: android.content.res.Resources*/  ) {
        val repository = DeskRepositoryImplementation()
        val loginResponse = repository.login(login, password)
        //TODO need to handle error!!!
//        _userId.value = loginResponse.userId
        _userRole.value = loginResponse.positionId
    }
}
