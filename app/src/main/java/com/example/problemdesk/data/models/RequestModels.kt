package com.example.problemdesk.data.models

data class LoginRequest(
    val username: String,
    val password: String,
    val fcm_token: String
)

data class CreateRequestRequest(
    val request_type: Int,
    val user_id: Int,
    val area_id: Int,
    val description: String
)