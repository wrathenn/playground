package com.wrathenn.fieldhash.model

import com.wrathenn.fieldhash.util.geography.GeographyPoint

data class FieldObject(
    val id: Long,
    val position: GeographyPoint,
    val hashChainId: Long,
    val fieldHash: String,
)

data class FieldObjectInsertable(
    val position: GeographyPoint,
    val hashChainId: Long,
    val fieldHash: String,
)
