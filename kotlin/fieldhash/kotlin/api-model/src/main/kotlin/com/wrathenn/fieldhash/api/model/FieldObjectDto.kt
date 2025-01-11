package com.wrathenn.fieldhash.api.model

import kotlinx.serialization.Serializable

@Serializable
data class FieldObjectDto(
    val id: Long,
    val position: GeometryPointDto,
    val hashChainId: Long,
    val fieldHash: String,
)

@Serializable
data class FieldObjectInsertableDto(
    val position: GeometryPointDto,
    val hashChainId: Long,
    val fieldHash: String,
)
