package com.example.problemdesk.data.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        //update server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        //respond ro recieved message
    }
}

//TODO i need to implement messaging as simple as possible (just throw message from 1 phone to 2)