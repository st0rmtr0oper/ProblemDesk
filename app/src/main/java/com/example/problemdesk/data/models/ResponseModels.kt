package com.example.problemdesk.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("role_id") val roleId: Int
)

data class MyDataResponse(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("name") val name: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("hire_date") val hireDate: String, // You might want to use LocalDate or another date type
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("birth_date") val birthDate: String, // Same as above
    @SerializedName("email") val email: String,
    @SerializedName("spec_id") val specId: Int,
    @SerializedName("fcm_token") val fcmToken: String,
    @SerializedName("role_id") val roleId: Int,
    @SerializedName("shift_id") val shiftId: Int
)

data class MyAwardsResponse(
    @SerializedName("tokens") val tokens: Int,
    @SerializedName("num_created") val numCreated: Int,
    @SerializedName("num_completed") val numCompleted: Int,
    @SerializedName("last_completed") val lastCompleted: String
)



data class DetailResponse(
    val detail: String
)

data class RequestResponse(
    val detail: Detail
)

data class Detail(
    val items: List<DetailItem>
)

data class DetailItem(
    val loc: List<Pair<String, Int>>,
    val msg: String,
    val type: String
)

data class Requests(
    val request: List<Request>
)

data class Request(
    val requestId: Int,
    val requestType: Int,
    val createdBy: Int,
    val assignedTo: Int?,
    val areaId: Int,
    val description: String,
    val statusId: Int,
    val createdAt: String,
    val updatedAt: String?,
    val deadline: String?
)
