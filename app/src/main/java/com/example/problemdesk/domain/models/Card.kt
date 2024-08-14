package com.example.problemdesk.domain.models

import com.google.gson.annotations.SerializedName

//TODO rethink these domain|data models. where they should be?

data class Card(
    @SerializedName("request_id") val requestId: Int,
    @SerializedName("request_type") val requestType: Int,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("assigned_to") val assignedTo: Int,     //null?
    @SerializedName("area_id") val areaId: Int,
    val description: String,
    @SerializedName("status_id") val statusId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val reason: String
)