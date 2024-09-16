package com.example.problemdesk.data.sharedprefs

import android.app.Application
import android.content.Context

fun getSharedPrefsUserId(context: Context): Int {
    val sharedPreferences = context.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
    val userId = sharedPreferences.getInt(USER_ID, 0)
    return userId
}

fun getSharedPrefsUserRole(context: Context): Int {
    val sharedPreferences = context.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
    val userRole = sharedPreferences.getInt(USER_ROLE, 0)
    return userRole
}

fun getSharedPrefsOldFcm(context: Context): String {
    val sharedPreferences = context.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
    val oldFcm = sharedPreferences.getString(OLD_FCM, "")
    return oldFcm ?: ""     //elvis operator returns "" id oldFcm is null
}

fun getSharedAuthToken(context: Context): String {
    val sharedPreferences = context.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
    val authToken = sharedPreferences.getString(TOKEN, "")
    return authToken ?: ""
}
//fun deleteShared

//TODO why i wrote this (get userId) only, if i want use it like that?
//need to add add/delete olf_fcm + delete userId
//TODO need to rework all sharedPrefs later (or even implement DataStorage, i don't know for now)