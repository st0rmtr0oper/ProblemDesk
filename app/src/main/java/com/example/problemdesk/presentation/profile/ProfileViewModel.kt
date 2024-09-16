package com.example.problemdesk.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.LogOutRequest
import com.example.problemdesk.data.models.LogOutResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _logoutStatus = MutableLiveData<Boolean>()
    val logoutStatus: LiveData<Boolean> get() = _logoutStatus

    private val repository = DeskRepositoryImpl(application)
    private lateinit var logoutResponse: LogOutResponse

    fun logOut(request: LogOutRequest){
        viewModelScope.launch {
            try {
                logoutResponse = repository.logout(request)
                _logoutStatus.postValue((logoutResponse.message.toString() == "FCM token removed successfully"))
//                val sharedPreferences = PreferenceUtil.getEncryptedSharedPreferences(application)
//                sharedPreferences?.edit()?.clear()?.apply()
            } catch (e: Exception) {
                _logoutStatus.postValue(false)
            }
        }
    }
}