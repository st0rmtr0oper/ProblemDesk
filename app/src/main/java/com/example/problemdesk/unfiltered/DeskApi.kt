package com.example.problemdesk.unfiltered

import retrofit2.http.POST

interface DeskApi {

    @POST("/send-message")
    suspend fun sendMessage(request: Request)
}