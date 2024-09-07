package com.example.problemdesk.presentation.profile.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.MyRewardsResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.launch

//TODO what if user_id = 0????

class AwardViewModel : ViewModel() {

    private val _awardData = MutableLiveData<MyRewardsResponse>()
    val awardData: LiveData<MyRewardsResponse> get() = _awardData

    fun loadInfo(userId: Int) {
        val repository = DeskRepositoryImplementation()
        var myRewardsResponse: MyRewardsResponse

        //i don't get the difference perfectly, but viewModelScope.launch should work only when ViewModel is Alive
        viewModelScope.launch {
            try {
                myRewardsResponse = repository.getMyRewards(userId)
                Log.i("!--{{{AWARD DATA}}}--!", myRewardsResponse.toString())
                _awardData.postValue(myRewardsResponse)
            } catch (e: Exception) {
                Log.i("!--{{{AWARD DATA}}}--!", e.toString())
            }
        }
    }
}