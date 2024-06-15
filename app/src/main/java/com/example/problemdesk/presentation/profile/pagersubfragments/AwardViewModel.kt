package com.example.problemdesk.presentation.profile.pagersubfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.domain.OLDMODELSrefactor.AwardData

class AwardViewModel : ViewModel() {

    private val _awardData = MutableLiveData<AwardData>()
    val awardData: LiveData<AwardData> get() = _awardData

    fun loadInfo() {
        //TODO award data login realization
        _awardData.value = AwardData(
            3600,
            150,
            160,
            "22/05/2024"
        )
    }
}