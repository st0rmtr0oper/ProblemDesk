package com.example.problemdesk.data.sharedprefs

import android.content.Context

fun getSharedPrefsUserId(context: Context): Int {
    val sharedPreferences = context.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
    val userId = sharedPreferences.getInt(USER_ID, 0)
    return userId
}