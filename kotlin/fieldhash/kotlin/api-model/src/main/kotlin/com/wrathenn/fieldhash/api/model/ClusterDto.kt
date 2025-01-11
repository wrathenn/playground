package com.wrathenn.fieldhash.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ClusterDto(
    val hash: String,
    val clusteredCount: Int,
    val clusterMarker: GeometryPointDto,
    val ids: List<Long>,
)
