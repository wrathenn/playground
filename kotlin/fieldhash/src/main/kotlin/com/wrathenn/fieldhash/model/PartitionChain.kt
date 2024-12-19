package com.wrathenn.fieldhash.model

import arrow.core.NonEmptyList

sealed class PartitionChainNode(val type: Type) {
    enum class Type {
        DEEPEN,
        EXPAND,
    }

    data class Deepening(
        val partition: Partition,
    ): PartitionChainNode(Type.DEEPEN)

    data class Expanding(
        val partitions: NonEmptyList<Partition>,
    ): PartitionChainNode(Type.EXPAND)
}

data class PartitionChain(
    val nodes: NonEmptyList<PartitionChainNode>,
)
