package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class InWorkViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    fun loadCards(userId: Int) {
        val repository = DeskRepositoryImpl(application)
        var response: List<Card>

        viewModelScope.launch {
            try {
                response = repository.getInProgress(userId)
//                Log.i("!--{{{IN WORK}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
//                Log.i("!--{{{IN WORK}}}--!", e.toString())
            }
        }
    }
}