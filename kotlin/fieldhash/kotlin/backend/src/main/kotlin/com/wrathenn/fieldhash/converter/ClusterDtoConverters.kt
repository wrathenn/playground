package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.api.model.ClusterDto
import com.wrathenn.fieldhash.model.Cluster

fun Cluster.toDto(): ClusterDto {
    return ClusterDto(
        hash = hash,
        clusteredCount = clusteredCount,
        clusterMarker = clusterMarkerPosition.toDto(),
        ids = ids,
    )
}
