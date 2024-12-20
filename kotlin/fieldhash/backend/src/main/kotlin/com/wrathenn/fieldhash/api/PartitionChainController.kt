package com.wrathenn.fieldhash.api

import com.wrathenn.fieldhash.model.PartitionChain
import com.wrathenn.fieldhash.model.PartitionChainInsertable
import com.wrathenn.fieldhash.service.PartitionChainService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/partitions/chains")
class PartitionChainController(
    private val partitionChainService: PartitionChainService,
) {
    @GetMapping
    fun findAll(): List<PartitionChain> {
        return partitionChainService.findAll()
    }

    @PostMapping
    fun create(chain: PartitionChainInsertable): PartitionChain {
        return partitionChainService.create(chain)
    }

    @DeleteMapping
    fun delete(id: Long): Unit {
        partitionChainService.delete(id)
    }
}