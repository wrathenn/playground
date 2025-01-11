package com.wrathenn.fieldhash.entity

import com.wrathenn.fieldhash.shared.GeometryPoint

data class FieldObjectEntity(
    val id: Long,
    val position: GeometryPoint,
    val hashChainId: Long,
    val fieldHash: String,
)

data class FieldObjectInsertableEntity(
    val position: GeometryPoint,
    val hashChainId: Long,
    val fieldHash: String,
)
