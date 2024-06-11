package com.example.problemdesk.domain.SQLMODELDidk

import java.time.LocalDateTime

data class User(
    val userId: Int,
    val username: String,
    val passwordHash: String,
    val surname: String,
    val name: String,
    val specialization: String?,
    val fcmToken: String?,
    val roleId: Int
)

data class Role(
    val roleId: Int,
    val roleName: String
)

data class Request(
    val requestId: Int,
    val requestType: Int,
    val createdBy: Int,
    val assignedTo: Int?,
    val areaId: Int,
    val description: String,
    val statusId: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deadline: LocalDateTime?
)

data class RequestType(
    val typeId: Int,
    val typeName: String
)

data class Area(
    val areaId: Int,
    val areaName: String
)

data class Status(
    val statusId: Int,
    val statusName: String
)

data class RequestStatusLog(
    val logId: Int,
    val requestId: Int,
    val oldStatusId: Int,
    val newStatusId: Int,
    val changedAt: LocalDateTime,
    val changedBy: Int
)
