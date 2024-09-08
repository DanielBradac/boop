package cz.bradacd.boop.model

import java.time.Instant

data class Boop(
    val name: String = "",
    val boopCount: Int = 0,
    val createDT: Instant = Instant.now(),
    val modifyDT: Instant = Instant.now(),
    val note: String = ""
) {
    fun compare() {

    }
}

data class SavedBoops(
    val boops: List<Boop>
)