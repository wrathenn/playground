package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.entity.FieldObjectEntity
import com.wrathenn.fieldhash.entity.FieldObjectInsertableEntity
import com.wrathenn.fieldhash.model.FieldObject
import com.wrathenn.fieldhash.model.FieldObjectInsertable

fun FieldObjectInsertable.toEntity(): FieldObjectInsertableEntity {
    return FieldObjectInsertableEntity(
        position = position,
        hashChainId = hashChainId,
        fieldHash = fieldHash,
    )
}

fun FieldObjectEntity.toModel(): FieldObject {
    return FieldObject(
        id = id,
        position = position,
        hashChainId = hashChainId,
        fieldHash = fieldHash,
    )
}
