package com.example.problemdesk.data.models

data class LoginRequest(
    val username: String,
    val password: String,
    val fcm_token: String
)
