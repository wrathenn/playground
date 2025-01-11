package com.wrathenn.fieldhash.model

import com.wrathenn.fieldhash.shared.GeometryPoint

data class FieldObject(
    val id: Long,
    val position: GeometryPoint,
    val hashChainId: Long,
    val fieldHash: String,
)

data class FieldObjectInsertable(
    val position: GeometryPoint,
    val hashChainId: Long,
    val fieldHash: String,
)
