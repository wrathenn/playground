package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.api.model.PartitionDto
import com.wrathenn.fieldhash.api.model.PartitionFormDto
import com.wrathenn.fieldhash.model.Partition
import com.wrathenn.fieldhash.model.PartitionForm

fun PartitionDto.toModel(): Partition {
    return Partition(
        xCount = xCount,
        yCount = yCount,
        coordsMap = coordsMap.mapKeys { (k, _) -> k.toModel() },
        backing = backing?.toModel(),
    )
}

fun PartitionFormDto.toModel(): PartitionForm {
    return PartitionForm(
        xCount = xCount,
        yCount = yCount,
        merges = merges.map { mergeDto ->
            mergeDto.map { coordsDto ->
                coordsDto.toModel()
            }.toNonEmptySet()
        },
    )
}

fun Partition.toDto(): PartitionDto {
    return PartitionDto(
        xCount = xCount,
        yCount = yCount,
        coordsMap = coordsMap.mapKeys { (k, _) -> k.toDto() },
        backing = backing?.toDto(),
    )
}

fun PartitionForm.toDto(): PartitionFormDto {
    return PartitionFormDto(
        xCount = xCount,
        yCount = yCount,
        merges = merges.map { mergeDto ->
            mergeDto.map { coordsDto ->
                coordsDto.toDto()
            }.toNonEmptySet()
        },
    )
}