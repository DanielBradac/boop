package cz.bradacd.boop.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.convert(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(this)
}

