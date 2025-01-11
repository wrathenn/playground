package com.wrathenn.fieldhash.util.geometry

import com.wrathenn.fieldhash.shared.GeometryLineString
import com.wrathenn.fieldhash.shared.GeometryMultiPolygon
import com.wrathenn.fieldhash.shared.GeometryPoint
import com.wrathenn.fieldhash.shared.GeometryPolygon
import org.locationtech.jts.geom.*

/** Default geography settings to store in database */
val geometryFactory0 = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING))

// From GeographyModels to Jts:

fun GeometryPoint.toJts(geometryFactory: GeometryFactory = geometryFactory0): Point {
    return geometryFactory.createPoint(Coordinate(x, y))
}

private fun Collection<GeometryPoint>.toJtsLinearRing(geometryFactory: GeometryFactory = geometryFactory0): LinearRing {
    return geometryFactory.createLinearRing(this.map { Coordinate(it.x, it.y) }.toTypedArray())
}

fun GeometryLineString.toJts(geometryFactory: GeometryFactory = geometryFactory0): LineString {
    return geometryFactory.createLineString(this.points.map { Coordinate(it.x, it.y) }.toTypedArray())
}

fun GeometryPolygon.toJts(geometryFactory: GeometryFactory = geometryFactory0): Polygon {
    return geometryFactory.createPolygon(shell.toJtsLinearRing(), holes.map { it.toJtsLinearRing() }.toTypedArray())
}

fun GeometryMultiPolygon.toJts(geometryFactory: GeometryFactory = geometryFactory0): MultiPolygon {
    return geometryFactory.createMultiPolygon(polygons.map { it.toJts(geometryFactory) }.toTypedArray())
}

// Now from Jts to GeographyModels:

fun Point.toGeometryPoint(): GeometryPoint {
    return GeometryPoint(x, y)
}

private fun LinearRing.toGeometryPoints(): List<GeometryPoint> {
    return this.coordinates.map { GeometryPoint(it.x, it.y) }
}

fun Polygon.toGeometryPolygon(): GeometryPolygon {
    return GeometryPolygon(
        shell = exteriorRing.coordinates.map { coordinate ->
            GeometryPoint(coordinate.x, coordinate.y)
        },
        holes = (0 until this.numInteriorRing).map { i ->
            val curHole: LinearRing = this.getInteriorRingN(i)
            curHole.toGeometryPoints()
        }
    )
}

fun MultiPolygon.toGeometryMultiPolygon(): GeometryMultiPolygon {
    return GeometryMultiPolygon(
        (0 until this.numGeometries).map {i ->
            val curGeometry: Polygon = this.getGeometryN(i) as Polygon
            curGeometry.toGeometryPolygon()
        }
    )
}

fun LineString.toGeometryLineString(): GeometryLineString {
    return GeometryLineString(
        this.coordinates.map { c ->
            GeometryPoint(x = c.x, y = c.y)
        }
    )
}
