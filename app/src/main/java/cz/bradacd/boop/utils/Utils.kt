package cz.bradacd.boop.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.convert(): String? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val date = this.atZone(ZoneId.systemDefault()).toLocalDate()  // Convert Instant to LocalDate
    return date.format(formatter)
}