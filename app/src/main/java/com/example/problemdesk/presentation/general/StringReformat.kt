package com.example.problemdesk.presentation.general

//TODO in ideal world, this should be done automatically with backend cus list of <...> can change

fun getSpecialization(id: Int): String {
    return when (id) {
        1 -> "Электрика"
        2 -> "Инструменты"
        3 -> "СанПиН условия"
        4 -> "Безопасность труда"
        5 -> "Документооборот"
        else -> "specialization error"
    }
}

fun getArea(id: Int): String {
    return "Участок №$id"
}

fun getStatusForCharts(status: String): String {
    return when (status) {
        "Исполненные" -> "done"
        "Отклоненные" -> "denied"
        "В работе" -> "in-progress"
        else -> "status error"
    }
}

//TODO getStatusBarColor
fun getStatusBarColor(id: Int): Int {
    return when (id) {
        1 -> 0
        2 -> 0
        3 -> 0
        4 -> 0
        5 -> 0
        6 -> 0
        7 -> 0
        else -> 0
    }
}

fun getStatus(id: Int): String {
    return when (id) {
        1 -> "На рассмотрении"
        2 -> "Утверждено"
        3 -> "Отклонено"
        4 -> "В работе"
        5 -> "Требует подтверждения"
        6 -> "Завершено"
        7 -> "Удалено"
        else -> "status error"
    }
}

//TODO getDate
fun getDate(date: String): String {
    var newDate: String
    //TODO
    newDate = "Создано: " + date
    return newDate
}