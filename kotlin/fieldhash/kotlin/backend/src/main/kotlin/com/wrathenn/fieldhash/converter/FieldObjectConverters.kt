package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.api.model.FieldObjectDto
import com.wrathenn.fieldhash.api.model.FieldObjectInsertableDto
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

fun FieldObject.toDto(): FieldObjectDto {
    return FieldObjectDto(
        id = id,
        position = position.toDto(),
        hashChainId = hashChainId,
        fieldHash = fieldHash,
    )
}

fun FieldObjectInsertableDto.toModel(): FieldObjectInsertable {
    return FieldObjectInsertable(
        position = position.toModel(),
        hashChainId = hashChainId,
        fieldHash = fieldHash,
    )
}
