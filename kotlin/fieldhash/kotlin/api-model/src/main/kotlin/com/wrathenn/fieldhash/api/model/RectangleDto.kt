package com.wrathenn.fieldhash.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RectangleDto(
    val upLeftX: Double,
    val upLeftY: Double,
    val downRightX: Double,
    val downRightY: Double,
)
