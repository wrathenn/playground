package com.wrathenn.fieldhash.converter

import arrow.core.toNonEmptyListOrNull
import com.wrathenn.fieldhash.api.model.PartitionChainDto
import com.wrathenn.fieldhash.api.model.PartitionChainInsertableDto
import com.wrathenn.fieldhash.api.model.PartitionChainNodeDto
import com.wrathenn.fieldhash.entity.PartitionChainEntity
import com.wrathenn.fieldhash.model.PartitionChain
import com.wrathenn.fieldhash.model.PartitionChainInsertable
import com.wrathenn.fieldhash.model.PartitionChainNode

fun PartitionChainEntity.toModel(): PartitionChain {
    val nelNodes = chain.toNonEmptyListOrNull()
        ?: throw IllegalStateException("Цепочка разбиений с id=$id пуста")
    return PartitionChain(
        id = id,
        nodes = nelNodes,
    )
}

fun PartitionChainNodeDto.toModel(): PartitionChainNode {
    return when (this) {
        is PartitionChainNodeDto.Deepening -> PartitionChainNode.Deepening(
            partition = this.partition.toModel(),
        )
        is PartitionChainNodeDto.Expanding -> PartitionChainNode.Expanding(
            partitions = this.partitions.map { it.toModel() },
        )
    }
}

fun PartitionChainNode.toDto(): PartitionChainNodeDto {
    return when (this) {
        is PartitionChainNode.Deepening -> PartitionChainNodeDto.Deepening(
            partition = this.partition.toDto(),
        )
        is PartitionChainNode.Expanding -> PartitionChainNodeDto.Expanding(
            partitions = this.partitions.map { it.toDto() },
        )
    }
}

fun PartitionChain.toDto(): PartitionChainDto {
    return PartitionChainDto(
        id = id,
        nodes = nodes.map { it.toDto() },
    )
}

fun PartitionChainInsertableDto.toModel(): PartitionChainInsertable {
    return PartitionChainInsertable(
        nodes = nodes.map { it.toModel() },
    )
}
