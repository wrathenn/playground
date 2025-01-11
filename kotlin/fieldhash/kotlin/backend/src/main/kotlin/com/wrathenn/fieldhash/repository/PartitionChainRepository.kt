package com.wrathenn.fieldhash.repository

import com.wrathenn.fieldhash.converter.toModel
import com.wrathenn.fieldhash.entity.PartitionChainEntity
import com.wrathenn.fieldhash.model.PartitionChain
import com.wrathenn.fieldhash.model.PartitionChainInsertable
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component

interface PartitionChainRepository {
    context(Handle) fun findAll(): List<PartitionChain>
    context(Handle) fun findById(id: Long): PartitionChain
    context(Handle) fun insert(chain: PartitionChainInsertable): PartitionChain
    context(Handle) fun update(id: Long, chain: PartitionChainInsertable): PartitionChain
    context(Handle) fun delete(id: Long): Unit
}

@Component
class PartitionChainRepositoryImpl: PartitionChainRepository {
    context(Handle)
    override fun findAll(): List<PartitionChain> {
        TODO("Not yet implemented")
    }

    context(Handle)
    override fun findById(id: Long): PartitionChain {
        return select("""
            select id, chain
            from master.partition_chain
            where id = :id
        """.trimIndent())
            .bind("id", id)
            .mapTo<PartitionChainEntity>()
            .one()
            .toModel()
    }

    context(Handle)
    override fun insert(chain: PartitionChainInsertable): PartitionChain {
        val id = createQuery("""
            insert into master.partition_chain(chain)
            values (:chain)
            returning id
        """.trimIndent())
            .bindKotlin(chain)
            .mapTo<Long>()
            .one()
        return findById(id)
    }

    context(Handle)
    override fun update(id: Long, chain: PartitionChainInsertable): PartitionChain {
        createUpdate("""
            update master.partition_chain
            set chain = :chain
            where id = :id
        """.trimIndent())
            .bindKotlin(chain)
            .execute()
        return findById(id)
    }

    context(Handle) override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
