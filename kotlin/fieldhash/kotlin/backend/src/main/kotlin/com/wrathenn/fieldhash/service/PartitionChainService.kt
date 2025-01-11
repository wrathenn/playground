package com.wrathenn.fieldhash.service

import com.wrathenn.fieldhash.model.PartitionChain
import com.wrathenn.fieldhash.model.PartitionChainInsertable
import com.wrathenn.fieldhash.repository.PartitionChainRepository
import com.wrathenn.fieldhash.util.transact
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Service

@Service
class PartitionChainService(
    private val partitionChainRepository: PartitionChainRepository,
    private val jdbi: Jdbi,
) {
    fun findAll(): List<PartitionChain> = jdbi.transact {
        partitionChainRepository.findAll()
    }

    fun create(chain: PartitionChainInsertable): PartitionChain = jdbi.transact {
        partitionChainRepository.insert(chain)
    }

    fun delete(id: Long): Unit = jdbi.transact {
        partitionChainRepository.delete(id)
    }
}