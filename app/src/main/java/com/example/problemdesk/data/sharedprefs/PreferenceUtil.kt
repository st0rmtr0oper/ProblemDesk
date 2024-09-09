package com.example.problemdesk.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object PreferenceUtil {

    //idk how to use it properly, its GPT's suggestion
    //any access to shared prefs need to be made inside ViewModels, not Fragments. It just have troubles with context
    //and i'm to lazy to find solution for it
    //TODO move shared prefs access inside ViewModels
    //TODO additionally its deprecated. cool
    fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "secure_prefs",
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    //also im tired of this null-check trail
    //TODO search solution to null-check problem. Or just left it all as it is. Who cares?
}
