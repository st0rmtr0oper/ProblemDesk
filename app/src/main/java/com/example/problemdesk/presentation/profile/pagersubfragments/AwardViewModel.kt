package com.example.problemdesk.presentation.profile.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.MyAwardsResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.launch

class AwardViewModel : ViewModel() {

    private val _awardData = MutableLiveData<MyAwardsResponse>()
    val awardData: LiveData<MyAwardsResponse> get() = _awardData


    //TODO what if user_id = 0????
    fun loadInfo(userId: Int) {
        //TODO award data login realization
        val repository = DeskRepositoryImplementation()
        var myAwardsResponse: MyAwardsResponse


        //i don't get the difference perfectly, but viewModelScope.launch should work only when ViewModel is Alive
        viewModelScope.launch {
//        CoroutineScope(Dispatchers.IO).launch {
            try {
                myAwardsResponse = repository.getMyAwards(userId)
                Log.i("!--{{{AWARD DATA}}}--!", myAwardsResponse.toString())
                _awardData.postValue(myAwardsResponse)
            } catch (e: Exception) {
                Log.i("!--{{{AWARD DATA}}}--!", e.toString())
            }
        }
//        _awardData.value = AwardData(
//            3600,
//            150,
//            160,
//            "22/05/2024"
//        )
    }
}