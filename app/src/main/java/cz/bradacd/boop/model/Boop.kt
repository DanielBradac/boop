package cz.bradacd.boop.model

import java.util.Calendar
import java.util.Date

data class Boop(
    val name: String = "",
    val boopCount: Int = 0,
    val createDT: Date = Calendar.getInstance().time,
    val modifyDT: Date = Calendar.getInstance().time,
    val note: String = ""
) {
    fun compare() {

    }
}

data class SavedBoops(
    val boops: List<Boop>
)