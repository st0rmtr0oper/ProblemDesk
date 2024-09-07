package com.example.problemdesk.domain.models

data class Specialization(
    val name: String,
    val id: Int
) {
    override fun toString(): String {
        return name
    }
}