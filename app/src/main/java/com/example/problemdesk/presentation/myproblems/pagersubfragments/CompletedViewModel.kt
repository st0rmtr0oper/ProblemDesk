package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.repository.DeskRepositoryImplementation
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class CompletedViewModel : ViewModel() {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    fun loadCards(userId: Int) {
        val repository = DeskRepositoryImplementation()
        var response: List<Card>

        viewModelScope.launch {
            try {
                response = repository.getCompleted(userId)
                Log.i("!--{{{COMPLETED}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{COMPLETED}}}--!", e.toString())
            }
        }
    }
}