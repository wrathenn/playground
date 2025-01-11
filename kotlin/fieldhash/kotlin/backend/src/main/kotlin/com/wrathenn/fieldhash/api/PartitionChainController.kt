package com.wrathenn.fieldhash.api

import com.wrathenn.fieldhash.api.model.PartitionChainDto
import com.wrathenn.fieldhash.api.model.PartitionChainInsertableDto
import com.wrathenn.fieldhash.converter.toDto
import com.wrathenn.fieldhash.converter.toModel
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
    fun findAll(): List<PartitionChainDto> {
        return partitionChainService.findAll().map { it.toDto() }
    }

    @PostMapping
    fun create(chain: PartitionChainInsertableDto): PartitionChainDto {
        return partitionChainService.create(chain.toModel()).toDto()
    }

    @DeleteMapping
    fun delete(id: Long): Unit {
        partitionChainService.delete(id)
    }
}