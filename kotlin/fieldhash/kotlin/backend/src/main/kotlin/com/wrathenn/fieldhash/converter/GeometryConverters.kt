package com.wrathenn.fieldhash.converter

import com.wrathenn.fieldhash.api.model.GeometryPointDto
import com.wrathenn.fieldhash.api.model.RectangleDto
import com.wrathenn.fieldhash.model.Rectangle
import com.wrathenn.fieldhash.shared.GeometryPoint

fun RectangleDto.toModel(): Rectangle {
    return Rectangle(
        upLeftX = upLeftX,
        upLeftY = upLeftY,
        downRightX = downRightX,
        downRightY = downRightY,
    )
}

fun GeometryPointDto.toModel(): GeometryPoint {
    return GeometryPoint(x = x, y = y)
}

fun GeometryPoint.toDto(): GeometryPointDto {
    return GeometryPointDto(x = x, y = y)
}
