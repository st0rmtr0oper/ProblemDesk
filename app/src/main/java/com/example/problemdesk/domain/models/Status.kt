package com.example.problemdesk.domain.models

data class Status(
    val name: String,
    val id: Int
) {
    override fun toString(): String {
        return name
    }
}