package com.example.problemdesk.domain.models

data class Workplace(
    val name: String,
    val id: Int
) {
    override fun toString(): String {
        return name
    }
}