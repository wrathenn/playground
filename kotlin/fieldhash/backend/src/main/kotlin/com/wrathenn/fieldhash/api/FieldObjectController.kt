package com.wrathenn.fieldhash.api

import com.wrathenn.fieldhash.model.Cluster
import com.wrathenn.fieldhash.model.FieldObject
import com.wrathenn.fieldhash.model.FieldObjectInsertable
import com.wrathenn.fieldhash.model.Rectangle
import com.wrathenn.fieldhash.service.FieldObjectService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fieldObjects")
class FieldObjectController(
    private val fieldObjectService: FieldObjectService,
) {
    @GetMapping
    fun getAllInRectangle(rectangle: Rectangle, partitionChainId: Long): List<FieldObject> {
        return fieldObjectService.getAllInRectangle(rectangle, partitionChainId)
    }

    @GetMapping("/clusters")
    fun getClusteredInRectangle(rectangle: Rectangle, partitionChainId: Long): List<Cluster> {
        return fieldObjectService.getClusteredInRectangle(rectangle, partitionChainId)
    }

    @PostMapping
    fun createFieldObject(fieldObject: FieldObjectInsertable): FieldObject {
        return fieldObjectService.createFieldObject(fieldObject)
    }

    @PostMapping
    fun updateFieldObject(id: Long, fieldObject: FieldObjectInsertable): FieldObject {
        return fieldObjectService.updateFieldObject(id, fieldObject)
    }

    @DeleteMapping
    fun deleteFieldObject(id: Long): Unit {
        fieldObjectService.deleteFieldObject(id)
    }
}