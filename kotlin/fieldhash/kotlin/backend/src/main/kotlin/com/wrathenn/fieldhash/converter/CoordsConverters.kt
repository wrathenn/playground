package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.api.model.CoordsDto
import com.wrathenn.fieldhash.shared.Coords

fun Coords.toDto(): CoordsDto {
    return CoordsDto(x = x, y = y)
}

fun CoordsDto.toModel(): Coords {
    return Coords(x = x, y = y)
}
