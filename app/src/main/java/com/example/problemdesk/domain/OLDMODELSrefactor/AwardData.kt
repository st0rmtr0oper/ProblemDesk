package com.example.problemdesk.domain.OLDMODELSrefactor

data class AwardData(
    val tokens: Int,
    val createdRequests: Int,
    val completedRequests: Int,
    val latestCompletedDate: String
)
