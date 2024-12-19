package com.wrathenn.fieldhash.model

import arrow.core.NonEmptySet
import com.wrathenn.fieldhash.logic.makeCoordsMap

typealias Merge = NonEmptySet<Coords>

data class PartitionForm(
    val xCount: Long,
    val yCount: Long,
    val merges: List<Merge>,
) {
    fun toModel() = Partition(
        xCount = xCount,
        yCount = yCount,
        coordsMap = this.makeCoordsMap(),
        backing = this,
    )
}

data class Partition(
    val xCount: Long,
    val yCount: Long,
    val coordsMap: Map<Coords, String>,
    val backing: PartitionForm? = null,
)
