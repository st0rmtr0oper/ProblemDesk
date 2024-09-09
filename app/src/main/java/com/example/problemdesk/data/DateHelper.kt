package com.example.problemdesk.data

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

//TODO is it necessary???            DELETE

object DateHelper {
    private const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    @JvmName("toIsoStringNullable")
    fun Date?.toIsoString(): String? {
        return this?.toIsoString()
    }

    fun Date.toIsoString(): String {
        val dateFormat: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
        return dateFormat.format(this)
    }
}

//Date().toIsoString()