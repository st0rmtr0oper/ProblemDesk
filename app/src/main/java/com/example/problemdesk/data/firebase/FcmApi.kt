package com.example.problemdesk.data.firebase

import retrofit2.http.Body
import retrofit2.http.POST

//TODO https://www.youtube.com/watch?v=q6TL2RyysV4&t=592s

interface FcmApi {
    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDTO
    )

    @POST("/broadcast")
    suspend fun broadcast(
        @Body body: SendMessageDTO
    )
}