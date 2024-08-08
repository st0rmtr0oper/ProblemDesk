package com.example.problemdesk.presentation.profile.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.data.models.MyDataRequest
import com.example.problemdesk.data.models.MyDataResponse
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileInfoViewModel : ViewModel() {

    private val _profileData = MutableLiveData<MyDataResponse>()
    val profileData: LiveData<MyDataResponse> get() = _profileData

    suspend fun loadInfo(userId: Int) {
        //TODO data load realization

//        val userId = sharedPreferences.getInt("user_id", -1)
        val repository = DeskRepositoryImplementation()
        var myDataResponse: MyDataResponse

        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val myDataRequest = MyDataRequest(userId.toString())
                myDataResponse = repository.getMyData(userId)
//                myDataResponse = repository.getMyData(myDataRequest)

                Log.i("!--{{{PROFILE DATA}}}--!", myDataResponse.toString())
                _profileData.postValue(myDataResponse)
            } catch (e: Exception) {
                Log.i("!--{{{PROFILE DATA}}}--!", e.toString())
            }
        }

//        _profileData.value = ProfileData(
//            "TMK-328654",
//            "Стаж с 02/04/2015",
//            "Алексеев Виктор Петрович",
//            "+ 7 985 422 5541",
//            "12/03/1967",
//            "alexeev.a@tmk.ru",
//            "Сварщик"
//        )
    }
}