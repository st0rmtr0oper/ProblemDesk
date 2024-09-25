package com.example.problemdesk.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
//import com.example.problemdesk.data.models.AuthTokenRequest
//import com.example.problemdesk.data.models.AuthTokenResponse
import com.example.problemdesk.data.models.LoginRequest
import com.example.problemdesk.data.models.LoginResponse
import com.example.problemdesk.data.notifications.getFcmToken
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.TOKEN
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.presentation.general.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    private val _userRole = MutableLiveData<Int>()
    val userRole: LiveData<Int> get() = _userRole

    private val _errorStatus = MutableLiveData<SingleLiveEvent<String>>()
    val errorStatus: LiveData<SingleLiveEvent<String>> get() = _errorStatus

//    private val _accessToken = MutableLiveData<String>()
//    private val accessToken: LiveData<String> get() = _accessToken

    suspend fun validate(
        login: String,
        password: String
    ) {
        val repository = DeskRepositoryImpl(application)
        var loginResponse: LoginResponse
//        var authTokenResponse: AuthTokenResponse
        var fcmToken: String?
        viewModelScope.launch {
            fcmToken = getFcm()
            if (fcmToken != null) {
                Log.d("!!!---[FCM token]---!!!", fcmToken!!)
                try {
                    val loginRequest = LoginRequest(login, password, fcmToken!!)
//                    var authTokenRequest = AuthTokenRequest(null, login, password,null,null,null)
                    loginResponse = repository.login(loginRequest)
//                    authTokenResponse = repository.authToken(authTokenRequest)
//                    Log.i("!--{{{LOGIN}}}--!", loginResponse.toString())
                    _userRole.postValue(loginResponse.roleId)
                    _userId.postValue(loginResponse.userId)
//                    _accessToken.postValue(loginResponse.accessToken)

                    val sharedPreferences = PreferenceUtil.getEncryptedSharedPreferences(application)
                    sharedPreferences.edit()?.putString(TOKEN, loginResponse.accessToken)?.apply()
//                    sharedPreferences.edit()?.putInt(USER_ID, userId.value ?: 0)?.apply()
                    //TODO need to test (userId 0)



                } catch (e: Exception) {
                    Log.i("!--{{{LOGIN}}}--!", e.toString())
                    _userRole.postValue(0)
                    if (e.toString()!="retrofit2.HttpEcxeption: HTTP 401 Unauthorized") {
                        _errorStatus.postValue(SingleLiveEvent(e.toString()))
                    } //TODO костыль пиздец

                    //postValue used because of anync work - live data update allowed only in main thread
                    //this thing somehow helps with this ussue
                    //how - idk
                }
            } else {
                Log.d("!!!---[FCM token]---!!!", "FCM token is NULL")
            }
        }
    }

    suspend fun getFcm(): String? {
        val fcm = getFcmToken()
        return fcm
    }
}
