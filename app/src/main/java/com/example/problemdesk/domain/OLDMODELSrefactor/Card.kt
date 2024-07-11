package com.example.problemdesk.domain.OLDMODELSrefactor

data class Card(
    val status: String,
//    val status: Status,
    val date: String,
    val specialization: String,
    val workplace: String,
//    val specialization: Specialization,
//    val workplace: Workplace,
    val taskText: String
)

//TODO draft variant
