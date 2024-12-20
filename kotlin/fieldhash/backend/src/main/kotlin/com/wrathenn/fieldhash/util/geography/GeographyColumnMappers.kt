package com.wrathenn.fieldhash.util.geography

import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.generic.GenericTypes
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.mapper.ColumnMapperFactory
import org.jdbi.v3.core.statement.StatementContext
import org.locationtech.jts.geom.*
import org.locationtech.jts.io.WKBReader
import org.postgresql.util.PGobject
import java.lang.reflect.Type
import java.sql.ResultSet
import java.util.*

class GeographyColumnMapper: ColumnMapperFactory {
    private fun String.hexToByteArray(): ByteArray {
        return this.chunked(2).map {it.toInt(16).toByte() }
            .toByteArray()
    }

    override fun build(type: Type, config: ConfigRegistry): Optional<ColumnMapper<*>> {
        val erasedType = GenericTypes.getErasedType(type)

        if (!GeographyModel::class.java.isAssignableFrom(erasedType)) {
            return Optional.empty()
        }

        return Optional.ofNullable(ColumnMapper<GeographyModel> { rs: ResultSet, col: Int, _: StatementContext? ->
            val obj: PGobject = rs.getObject(col) as? PGobject ?: return@ColumnMapper null

            val reader = WKBReader(GeometryFactory())

            val a = reader.read(obj.value!!.hexToByteArray())
            when (a) {
                is Point -> a.toGeographyPoint()
                is Polygon -> a.toGeographyPolygon()
                is MultiPolygon -> a.toGeographyMultiPolygon()
                is LineString -> a.toGeographyLineString()
                else -> throw IllegalArgumentException("Unsupported Geometry or Geography type ${a.geometryType}")
            }
        })
    }
}