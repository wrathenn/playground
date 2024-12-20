package com.wrathenn.fieldhash.service

import com.wrathenn.fieldhash.logic.ConditionsBuilder
import com.wrathenn.fieldhash.model.Cluster
import com.wrathenn.fieldhash.model.FieldObject
import com.wrathenn.fieldhash.model.FieldObjectInsertable
import com.wrathenn.fieldhash.model.Rectangle
import com.wrathenn.fieldhash.repository.FieldObjectRepository
import com.wrathenn.fieldhash.repository.PartitionChainRepository
import com.wrathenn.fieldhash.util.transact
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Service

@Service
class FieldObjectService(
    private val fieldObjectRepository: FieldObjectRepository,
    private val partitionChainRepository: PartitionChainRepository,
    private val jdbi: Jdbi,
    private val conditionsBuilder: ConditionsBuilder,
) {
    fun getAllInRectangle(rectangle: Rectangle, partitionChainId: Long): List<FieldObject> = jdbi.transact {
        val chain = partitionChainRepository.findById(partitionChainId)
        val hashConditions = conditionsBuilder.createConditions(rectangle, chain)
        fieldObjectRepository.findAll(hashConditions, partitionChainId)
    }

    fun getClusteredInRectangle(rectangle: Rectangle, partitionChainId: Long): List<Cluster> = jdbi.transact {
        val chain = partitionChainRepository.findById(partitionChainId)
        val hashConditions = conditionsBuilder.createConditions(rectangle, chain)
        fieldObjectRepository.findClustersByConditions(hashConditions, partitionChainId, 100, 8)
    }

    fun createFieldObject(fieldObject: FieldObjectInsertable): FieldObject = jdbi.transact {
        fieldObjectRepository.insert(fieldObject)
    }

    fun updateFieldObject(id: Long, fieldObject: FieldObjectInsertable): FieldObject = jdbi.transact {
        fieldObjectRepository.update(id, fieldObject)
    }

    fun deleteFieldObject(id: Long): Unit = jdbi.transact {
        fieldObjectRepository.delete(id)
    }
}
