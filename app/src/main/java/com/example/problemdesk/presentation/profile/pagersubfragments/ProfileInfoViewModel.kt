package com.example.problemdesk.presentation.profile.pagersubfragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import kotlinx.coroutines.launch

class ProfileInfoViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _profileData = MutableLiveData<MyDataResponse>()
    val profileData: LiveData<MyDataResponse> get() = _profileData

    suspend fun loadInfo(userId: Int) {
        val repository = DeskRepositoryImpl(application)
        var myDataResponse: MyDataResponse

        viewModelScope.launch {
            try {
                myDataResponse = repository.getMyData(userId)
//                Log.i("!--{{{PROFILE DATA}}}--!", myDataResponse.toString())
                _profileData.postValue(myDataResponse)
            } catch (e: Exception) {
//                Log.i("!--{{{PROFILE DATA}}}--!", e.toString())
            }
        }
    }
}