package com.wrathenn.fieldhash.api.model

import kotlinx.serialization.Serializable

@Serializable
data class GeometryPointDto(
    val x: Double,
    val y: Double
)
