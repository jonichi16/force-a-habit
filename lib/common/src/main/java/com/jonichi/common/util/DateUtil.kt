package com.jonichi.common.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun timeFormatter(time: LocalTime): String {
    return time.format(DateTimeFormatter.ofPattern("hh:mm a"))
}
