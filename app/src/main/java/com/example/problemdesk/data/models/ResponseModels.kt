package com.example.problemdesk.data.models

import com.google.gson.annotations.SerializedName

//nulls?
//TODO something with dates (ISO 8601)

data class LoginResponse(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("role_id") val roleId: Int,
    @SerializedName("access_token") val accessToken: String
)

data class LogOutResponse(
    val message: String
)

data class AuthTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String
)

data class RefreshResponse(
    val message: String  //FCM token refreshed successfully
)

data class CreateRequestResponse(
    val message: String,
    @SerializedName("request_id") val requestId: Int
)

data class TaskManipulationResponse(
    val message: String,
    val request: Int
)

data class MyDataResponse(
    @SerializedName("user_id") val userId: Int,
    val username: String,
    val surname: String,
    val name: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("hire_date") val hireDate: String, // You might want to use LocalDate or another date type
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("birth_date") val birthDate: String, // Same as above
    val email: String,
    @SerializedName("spec_id") val specId: Int,
    @SerializedName("fcm_token") val fcmToken: List<String>,
    @SerializedName("role_id") val roleId: Int,
    @SerializedName("shift_id") val shiftId: Int
)

data class MyRewardsResponse(
    val tokens: Int,
    @SerializedName("num_created") val numCreated: Int,
    @SerializedName("num_completed") val numCompleted: Int,
    @SerializedName("last_completed") val lastCompleted: String
)


