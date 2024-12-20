package com.wrathenn.fieldhash.converter

import arrow.core.toNonEmptyListOrNull
import com.wrathenn.fieldhash.entity.PartitionChainEntity
import com.wrathenn.fieldhash.model.PartitionChain

fun PartitionChainEntity.toModel(): PartitionChain {
    val nelNodes = chain.toNonEmptyListOrNull()
        ?: throw IllegalStateException("Цепочка разбиений с id=$id пуста")
    return PartitionChain(
        id = id,
        nodes = nelNodes,
    )
}