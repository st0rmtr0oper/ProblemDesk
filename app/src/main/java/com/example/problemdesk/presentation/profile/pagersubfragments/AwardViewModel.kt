package com.example.problemdesk.presentation.profile.pagersubfragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.MyRewardsResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import kotlinx.coroutines.launch

class AwardViewModel (private val application: Application) : AndroidViewModel(application) {

    private val _awardData = MutableLiveData<MyRewardsResponse>()
    val awardData: LiveData<MyRewardsResponse> get() = _awardData

    fun loadInfo(userId: Int) {
        val repository = DeskRepositoryImpl(application)
        var myRewardsResponse: MyRewardsResponse

        viewModelScope.launch {
            try {
                myRewardsResponse = repository.getMyRewards(userId)
                _awardData.postValue(myRewardsResponse)
            } catch (e: Exception) {
            }
        }
    }
}