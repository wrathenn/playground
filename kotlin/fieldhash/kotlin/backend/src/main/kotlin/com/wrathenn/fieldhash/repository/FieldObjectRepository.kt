package com.wrathenn.fieldhash.repository

import com.wrathenn.fieldhash.converter.toModel
import com.wrathenn.fieldhash.entity.FieldObjectEntity
import com.wrathenn.fieldhash.model.*
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import org.springframework.stereotype.Component

interface FieldObjectRepository {
    context(Handle)
    fun findAll(
        conditions: List<HashCondition>,
        hashChainId: Long,
    ): List<FieldObject>

    context(Handle)
    fun findClustersByConditions(
        conditions: List<HashCondition>,
        hashChainId: Long,
        maxCount: Int,
        hashLength: Int,
    ): List<Cluster>

    context(Handle) fun findById(id: Long): FieldObject
    context(Handle) fun insert(fieldObject: FieldObjectInsertable): FieldObject
    context(Handle) fun update(id: Long, fieldObject: FieldObjectInsertable): FieldObject
    context(Handle) fun delete(id: Long): Unit
}

@Component
class FieldObjectRepositoryImpl: FieldObjectRepository {

    context(Handle)
    override fun findAll(
        conditions: List<HashCondition>,
        hashChainId: Long,
    ): List<FieldObject> {
        return select("""
            select id, position, hash_chain_id, field_hash
            from master.field_object
            where hash_chain_id = :hashChainId
              and <hash_conditions>
        """.trimIndent())
            .bind("hashChainId", hashChainId)
            .define("hash_conditions", conditions.collectQueryCondition("field_hash"))
            .mapTo<FieldObject>()
            .list()
    }

    context(Handle)
    override fun findClustersByConditions(
        conditions: List<HashCondition>,
        hashChainId: Long,
        maxCount: Int,
        hashLength: Int,
    ): List<Cluster> {
        return select("""
            select
              substring(field_hash, 1, :hashLength) as hash,
              count(*) as clustered_count,
              st_centroid(st_collect(position)) as cluster_marker_position,
              (array_agg(id))[\\::unpackedItemsCount] as ids
            from master.field_object
            where hash_chain_id = :hashChainId
              and <hash_conditions>
            group by sgh_id
        """.trimIndent())
            .bind("hashLength", hashLength)
            .bind("unpackedItemsCount", maxCount)
            .bind("hashChainId", hashChainId)
            .define("hash_conditions", conditions.collectQueryCondition("field_hash"))
            .mapTo<Cluster>()
            .list()
    }

    context(Handle)
    override fun findById(id: Long): FieldObject {
        return select("""
            select id, position, hash_chain_id, field_hash
            where id = :id
        """.trimIndent())
            .bind("id", id)
            .mapTo<FieldObjectEntity>()
            .one()
            .toModel()
    }

    context(Handle)
    override fun insert(fieldObject: FieldObjectInsertable): FieldObject {
        val id = createQuery("""
            insert into master.field_object(position, hash_chain_id, field_hash)
            values (:position, :hashChainId, :fieldHash)
            returning id
        """.trimIndent())
            .bindKotlin(fieldObject)
            .mapTo<Long>()
            .one()

        return findById(id)
    }

    context(Handle) override fun update(id: Long, fieldObject: FieldObjectInsertable): FieldObject {
        TODO("Not yet implemented")
    }

    context(Handle) override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

}
