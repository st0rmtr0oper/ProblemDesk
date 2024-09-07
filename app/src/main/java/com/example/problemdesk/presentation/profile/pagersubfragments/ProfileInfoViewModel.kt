package com.example.problemdesk.presentation.profile.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.launch

class ProfileInfoViewModel : ViewModel() {

    private val _profileData = MutableLiveData<MyDataResponse>()
    val profileData: LiveData<MyDataResponse> get() = _profileData

    suspend fun loadInfo(userId: Int) {
        val repository = DeskRepositoryImplementation()
        var myDataResponse: MyDataResponse

        viewModelScope.launch {
            try {
                myDataResponse = repository.getMyData(userId)
                Log.i("!--{{{PROFILE DATA}}}--!", myDataResponse.toString())
                _profileData.postValue(myDataResponse)
            } catch (e: Exception) {
                Log.i("!--{{{PROFILE DATA}}}--!", e.toString())
            }
        }
    }
}