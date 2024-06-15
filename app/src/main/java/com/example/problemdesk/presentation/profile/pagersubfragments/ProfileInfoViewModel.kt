package com.example.problemdesk.presentation.profile.pagersubfragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.problemdesk.domain.OLDMODELSrefactor.ProfileData

class ProfileInfoViewModel : ViewModel() {

    private val _profileData = MutableLiveData<ProfileData>()
    val profileData: LiveData<ProfileData> get() = _profileData

    fun loadInfo() {
        //TODO data load realization
        _profileData.value = ProfileData(
            "TMK-328654",
            "Стаж с 02/04/2015",
            "Алексеев Виктор Петрович",
            "+ 7 985 422 5541",
            "12/03/1967",
            "alexeev.a@tmk.ru",
            "Сварщик"
        )
    }
}