package com.example.problemdesk.data.models

//TODO why i use here snake_case and CamelCase in responses?

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

data class LogOutRequest(
    val user_id: Int,
    val old_fcm: String
)