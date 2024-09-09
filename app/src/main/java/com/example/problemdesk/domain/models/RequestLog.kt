package com.example.problemdesk.domain.models

import com.google.gson.annotations.SerializedName

data class RequestLog(
    @SerializedName("log_id") val logId: Int,
    @SerializedName("request_id") val requestId: Int,
    @SerializedName("old_status_id") val oldStatusId: Int,
    @SerializedName("new_status_id") val newStatusId: Int,
    @SerializedName("changed_at") val changedAt: String, //TODO date
    @SerializedName("changed_by") val changedBy: Int,
    val reason: String,
    @SerializedName("changer_name") val changerName: String,
    @SerializedName("action_name") val actionName: String
)