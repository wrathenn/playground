package com.wrathenn.fieldhash.entity

import com.wrathenn.fieldhash.model.PartitionChainNode
import org.jdbi.v3.json.Json

data class PartitionChainEntity(
    val id: Long,
    @Json
    val chain: List<PartitionChainNode>
)
