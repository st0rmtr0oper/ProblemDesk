package com.example.problemdesk.data.notifications

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

suspend fun getFcmToken(): String? {
    return try {
        FirebaseMessaging.getInstance().token.await() // Use the await extension function
    } catch (e: Exception) {
        Log.w(TAG, "Fetching FCM registration token failed", e)
        null
    }
}

//fun getFcmToken(callback: TokenCallback) {
//    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//        if (!task.isSuccessful) {
//            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//            callback.onError(task.exception)
//            return@addOnCompleteListener
//        }
//        val token = task.result
//        Log.d("!!!---[FCM token]---!!!", token)
//        callback.onTokenReceived(token)
//    }
//}