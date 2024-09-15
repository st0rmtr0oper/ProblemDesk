package com.example.problemdesk.presentation.mastermenu.pagersubfragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.problemdesk.data.repository.DeskRepositoryImpl
import com.example.problemdesk.domain.models.Card
import kotlinx.coroutines.launch

class MasterApproveViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    fun loadCards(userId: Int) {
        val repository = DeskRepositoryImpl(application)
        var response: List<Card>

        viewModelScope.launch {
            try {
                response = repository.getUnderMasterApproval(userId)
                Log.i("!--{{{UNAPPROVED}}}--!", response.toString())
                _cards.postValue(response)
            } catch (e: Exception) {
                Log.i("!--{{{UNAPPROVED}}}--!", e.toString())
            }
        }
    }
}