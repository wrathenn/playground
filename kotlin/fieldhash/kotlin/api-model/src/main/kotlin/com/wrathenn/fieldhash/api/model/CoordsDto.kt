package com.wrathenn.fieldhash.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CoordsDto(
    val x: Long,
    val y: Long,
)
