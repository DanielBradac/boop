package cz.bradacd.boop.model

import java.time.Instant

data class Boop(
    val name: String = "",
    val boopCount: Int = 0,
    val createDT: Instant = Instant.now(),
    val modifyDT: Instant = Instant.now(),
): Comparable<Boop> {
    override fun compareTo(other: Boop): Int {
        return this.name.compareTo(other.name)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Boop) return false

        return name == other.name
    }
    override fun hashCode(): Int {
        return name.hashCode()
    }
}

data class SavedBoops(
    val boops: List<Boop>
)