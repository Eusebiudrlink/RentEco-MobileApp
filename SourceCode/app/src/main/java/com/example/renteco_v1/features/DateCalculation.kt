package com.example.renteco_v1.features

import kotlinx.datetime.toInstant

fun getMonth(startTime: String): String {
    val monthNum= startTime.substring(5,7)
    //generat de GPT AI
    return when (monthNum) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "Invalid month number"
    }
}
fun getDuration(startTime: String, endTime: String): String {
    val duration = endTime.toInstant() - startTime.toInstant()
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    return "${hours}:${minutes}"
}