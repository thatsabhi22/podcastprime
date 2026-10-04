package com.app.pro.podcastprime.util

fun Long.toDurationMinutes(): String {
    val minutes = (this / 60).toInt()

    return "$minutes min"
}