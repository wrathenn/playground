package com.wrathenn.fieldhash.model

import com.wrathenn.fieldhash.util.geography.GeographyPoint

data class Cluster(
    val hash: String,
    val clusteredCount: Int,
    val clusterMarkerPosition: GeographyPoint,
    val ids: List<Long>,
)
