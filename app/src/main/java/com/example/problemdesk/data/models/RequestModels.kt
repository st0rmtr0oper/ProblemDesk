package com.example.problemdesk.data.models

//TODO why i use here snake_case and CamelCase in responses?

data class LoginRequest(
    val username: String,
    val password: String,
    val fcm_token: String
)

data class LogOutRequest(
    val user_id: Int,
    val old_fcm: String
)

//data class AuthTokenRequest(
//    val grant_type: String?,
//    val username: String,
//    val password: String,
//    val scope: String?,
//    val client_id: String?,
//    val client_secter: String?
//)

data class RefreshRequest(
    val user_id: Int,
    val new_fcm: String
)

data class CreateRequestRequest(
    val request_type: Int,
    val user_id: Int,
    val area_id: Int,
    val description: String
)

data class TaskManipulationRequest(
    val user_id: Int,
    val request_id: Int,
    val reason: String
)