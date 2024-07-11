package com.example.problemdesk.data

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
