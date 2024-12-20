package com.wrathenn.fieldhash.logic

import arrow.core.tail
import arrow.core.toNonEmptySetOrNull
import com.wrathenn.fieldhash.model.*
import kotlin.math.floor

private fun Partition.hashToMerges(): Map<String, Merge> {
    return coordsMap.asSequence()
        .groupBy({ it.value }, { it.key })
        .mapValues { (_, v) -> v.toNonEmptySetOrNull()!! }
}

private tailrec fun calculateHashRec(rect: Rectangle, chain: List<Partition>, point: Point, resHash: String): String {
    if (chain.isEmpty()) return resHash

    val partition = chain.first()
    val lx = (rect.downRightX - rect.upLeftX) / partition.xCount
    val ly = (rect.upLeftY - rect.downRightY) / partition.yCount
    val cx = floor((point.x - rect.upLeftX) / lx).toLong()
    val cy = floor((point.y - rect.downRightY) / ly).toLong()

    val coords = Coords(cx, cy)
    val hash = partition.coordsMap[coords]
    if (hash == null) {
        throw IllegalStateException("Не найден хеш для координат $coords в разбиении $partition")
    }

    // Теперь надо найти, до какого прямоугольника сократили область
    val mergeMap = partition.hashToMerges()
    val merge = mergeMap[hash]!!

    var upLeftX: Double = Double.POSITIVE_INFINITY
    var upLeftY: Double = Double.POSITIVE_INFINITY
    var downRightX: Double = Double.NEGATIVE_INFINITY
    var downRightY: Double = Double.NEGATIVE_INFINITY

    val rectangles = merge.map { (x, y) ->
        val _upLeftX = x * lx; if (_upLeftX < upLeftX) upLeftX = _upLeftX
        val _upLeftY = y * ly; if (_upLeftY < upLeftY) upLeftY = _upLeftY
        val _downRightX = x * lx + x; if (_downRightX > downRightX) downRightX = _downRightX
        val _downRightY = y * ly + y; if (_downRightY > downRightY) downRightY = _downRightY

        Rectangle(upLeftX = _upLeftX, upLeftY = _upLeftY, downRightX = _downRightX, downRightY = _downRightY)
    }

    val maxRectangle = Rectangle(upLeftX = upLeftX, upLeftY = upLeftY, downRightX = downRightX, downRightY = downRightY)

    return calculateHashRec(maxRectangle, chain.tail(), point, resHash + hash)
}

fun calculateHash(initialRect: Rectangle, chain: PartitionChain, point: Point): String {
    val partitions = chain.nodes.map { node ->
        when (node) {
            is PartitionChainNode.Deepening -> node.partition
            is PartitionChainNode.Expanding -> node.combine()
        }
    }
    return calculateHashRec(initialRect, partitions, point, "")
}
