package com.wrathenn.fieldhash.entity

import com.wrathenn.fieldhash.util.geography.GeographyPoint

data class FieldObjectEntity(
    val id: Long,
    val position: GeographyPoint,
    val hashChainId: Long,
    val fieldHash: String,
)

data class FieldObjectInsertableEntity(
    val position: GeographyPoint,
    val hashChainId: Long,
    val fieldHash: String,
)
