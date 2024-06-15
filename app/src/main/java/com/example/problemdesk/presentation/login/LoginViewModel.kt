package com.example.problemdesk.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.R
import com.example.problemdesk.domain.OLDMODELSrefactor.Role

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Role>()
    val loginResult: LiveData<Role> get() = _loginResult

    fun validate(login: String, password: String, resources: android.content.res.Resources) {
        when {
            login == resources.getString(R.string.login1) && password == resources.getString(R.string.password1) -> {
                _loginResult.value = Role.COMPLAINER
            }
            login == resources.getString(R.string.login2) && password == resources.getString(R.string.password2) -> {
                _loginResult.value = Role.EXECUTOR
            }
            login == resources.getString(R.string.login3) && password == resources.getString(R.string.password3) -> {
                _loginResult.value = Role.MASTER
            }
            login == resources.getString(R.string.login4) && password == resources.getString(R.string.password4) -> {
                _loginResult.value = Role.MANAGER
            }
            else -> {
                _loginResult.value = Role.NONE
            }
        }
    }
}
