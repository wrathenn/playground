package com.wrathenn.fieldhash.model

import com.wrathenn.fieldhash.shared.GeometryPoint

data class Cluster(
    val hash: String,
    val clusteredCount: Int,
    val clusterMarkerPosition: GeometryPoint,
    val ids: List<Long>,
)
