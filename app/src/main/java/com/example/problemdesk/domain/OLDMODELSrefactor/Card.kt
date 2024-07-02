package com.example.problemdesk.domain.OLDMODELSrefactor

data class Card(
    val status: Status,
    val date: String,
    val specialization: Specialization,
    val workplace: Workplace,
    val taskText: String
)

//TODO draft variant
