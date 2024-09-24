package com.example.problemdesk.domain.models

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("user_id") val userId: String,
    val surname: String?,
    val name: String?,
    @SerializedName("middle_name") val middleName: String?,
    val tokens: Int,
    @SerializedName("num_created") val numCreated: Int,
    @SerializedName("num_completed") val numCompleted: Int
)