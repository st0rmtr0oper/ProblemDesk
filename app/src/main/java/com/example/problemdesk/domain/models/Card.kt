package com.example.problemdesk.domain.models

data class Card(
    val status: Status,
    val date: String,
    val specialization: Specialization,
    val workplace: Workplace,
    val taskText: String,
)

//TODO draft variant
