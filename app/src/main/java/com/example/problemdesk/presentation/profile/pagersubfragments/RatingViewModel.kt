package com.example.problemdesk.presentation.profile.pagersubfragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.models.RatingResponse
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import kotlinx.coroutines.launch

class RatingViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _ratingData = MutableLiveData<RatingResponse>()
    val ratingData: LiveData<RatingResponse> get() = _ratingData

    fun loadRating() {
        val repository = DeskRepositoryImpl(application)
        val response: RatingResponse

        viewModelScope.launch {
            try {
//                response = repository.loadRating()
//                _ratingData.postValue(response)
            } catch (e: Exception) {
            }
        }
    }
}