package com.wrathenn.fieldhash.logic

import arrow.core.NonEmptySet
import com.wrathenn.fieldhash.logic.math.lcm
import com.wrathenn.fieldhash.model.*

fun PartitionForm.makeCoordsMap(): Map<Coords, String> {
    val result = mutableMapOf<Coords, String>()
    var hashIndex = 0
    // Побыстрее находить все мержи, которые содержатся в одном множестве с данным
    val coordsToMerge: Map<Coords, NonEmptySet<Coords>> = merges.flatMap { merge -> merge.map { el -> el to merge } }.toMap()

    for (x in 0 until xCount) {
        for (y in 0 until yCount) {
            val coords = Coords(x, y)
            val merge = coordsToMerge[coords]

            // Вдруг какой-то из примерженных уже взял себе хеш
            val alreadyMergedHash = merge?.firstNotNullOfOrNull { mergedCoords -> result[mergedCoords] }
            val hash = alreadyMergedHash ?: run {
                hashIndex += 1
                HashPart.hashes[hashIndex - 1]
            }

            result[coords] = hash.toString()
        }
    }

    return result
}

fun PartitionChainNode.Expanding.combine(): Partition {
    val resultCoordsMap = mutableMapOf<Coords, String>()

    val xLcm = lcm(this.partitions.map { it.xCount })
    val yLcm = lcm(this.partitions.map { it.yCount })

    for (partition in this.partitions) {
        val xScale = xLcm / partition.xCount
        val yScale = yLcm / partition.yCount

        for ((coords, hash) in partition.coordsMap) {
            for (x in coords.x * xScale until (coords.x + 1) * xScale) {
                for (y in coords.y * yScale until (coords.y + 1) * yScale) {
                    val newCoords = Coords(x, y)
                    resultCoordsMap.compute(newCoords) { _, currentHash ->
                        (currentHash ?: "") + hash
                    }
                }
            }
        }
    }

    return Partition(
        xCount = xLcm,
        yCount = yLcm,
        coordsMap = resultCoordsMap,
    )
}
