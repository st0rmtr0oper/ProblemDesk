package com.example.problemdesk.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.notifications.getFcmToken
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

//    private val _userId = MutableLiveData<Int>()
//    val userId: LiveData<Int> get() = _userId

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> get() = _userRole

    suspend fun validate(
        login: String,
        password: String
    ) {
        val repository = DeskRepositoryImplementation()
        var loginResponse: LoginResponse
        var fcmToken: String?
        CoroutineScope(Dispatchers.IO).launch {
            fcmToken = getFcmToken()
            if (fcmToken != null) {
                Log.d("!!!---[FCM token]---!!!", fcmToken!!)
                try {
                    val loginRequest = LoginRequest(login, password, fcmToken!!)
                    loginResponse = repository.login(loginRequest)
                    Log.i("!--{{{LOGIN}}}--!", loginResponse.toString())
                    _userRole.postValue(loginResponse.roleId)
                } catch (e: Exception) {
                    Log.i("!--{{{LOGIN}}}--!", e.toString())
                    _userRole.postValue(0)

                    //postValue used because of anync work - live data update allowed only in main thread
                    //this thing somehow helps with this ussue
                    //how - idk
                }
            } else {
                Log.d("!!!---[FCM token]---!!!", "FCM token is NULL")
            }
        }
    }
}
