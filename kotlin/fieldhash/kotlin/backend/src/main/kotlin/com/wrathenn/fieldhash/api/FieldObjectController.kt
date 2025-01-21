package com.wrathenn.fieldhash.api

import com.wrathenn.fieldhash.api.model.ClusterDto
import com.wrathenn.fieldhash.api.model.FieldObjectDto
import com.wrathenn.fieldhash.api.model.FieldObjectInsertableDto
import com.wrathenn.fieldhash.api.model.RectangleDto
import com.wrathenn.fieldhash.converter.toDto
import com.wrathenn.fieldhash.converter.toModel
import com.wrathenn.fieldhash.service.FieldObjectService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fieldObjects")
class FieldObjectController(
    private val fieldObjectService: FieldObjectService,
) {
    @GetMapping
    fun getAllInRectangle(rectangle: RectangleDto, partitionChainId: Long): List<FieldObjectDto> {
        return fieldObjectService.getAllInRectangle(rectangle.toModel(), partitionChainId)
            .map { it.toDto() }
    }

    @GetMapping("/clusters")
    fun getClusteredInRectangle(rectangle: RectangleDto, partitionChainId: Long): List<ClusterDto> {
        return fieldObjectService.getClusteredInRectangle(rectangle.toModel(), partitionChainId)
            .map { it.toDto() }
    }

    @PostMapping
    fun createFieldObject(fieldObject: FieldObjectInsertableDto): FieldObjectDto {
        return fieldObjectService.createFieldObject(fieldObject.toModel()).toDto()
    }

    @PostMapping("/{id}")
    fun updateFieldObject(@PathVariable id: Long, fieldObject: FieldObjectInsertableDto): FieldObjectDto {
        return fieldObjectService.updateFieldObject(id, fieldObject.toModel()).toDto()
    }

    @DeleteMapping
    fun deleteFieldObject(id: Long): Unit {
        fieldObjectService.deleteFieldObject(id)
    }
}