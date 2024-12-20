package com.wrathenn.fieldhash.util.geography

import org.locationtech.jts.geom.*

/** Default geography settings to store in database */
val geometryFactory4326 = GeometryFactory(PrecisionModel(PrecisionModel.FLOATING), 4326)

// From GeographyModels to Jts:

fun GeographyPoint.toJts(geometryFactory: GeometryFactory = geometryFactory4326): Point {
    return geometryFactory.createPoint(Coordinate(lon, lat))
}

private fun Collection<GeographyPoint>.toJtsLinearRing(geometryFactory: GeometryFactory = geometryFactory4326): LinearRing {
    return geometryFactory.createLinearRing(this.map { Coordinate(it.lon, it.lat) }.toTypedArray())
}

fun GeographyLineString.toJts(geometryFactory: GeometryFactory = geometryFactory4326): LineString {
    return geometryFactory.createLineString(this.points.map { Coordinate(it.lon, it.lat) }.toTypedArray())
}

fun GeographyPolygon.toJts(geometryFactory: GeometryFactory = geometryFactory4326): Polygon {
    return geometryFactory.createPolygon(shell.toJtsLinearRing(), holes.map { it.toJtsLinearRing() }.toTypedArray())
}

fun GeographyMultiPolygon.toJts(geometryFactory: GeometryFactory = geometryFactory4326): MultiPolygon {
    return geometryFactory.createMultiPolygon(polygons.map { it.toJts(geometryFactory) }.toTypedArray())
}

// Now from Jts to GeographyModels:

fun Point.toGeographyPoint(): GeographyPoint {
    return GeographyPoint(x, y)
}

private fun LinearRing.toGeographyPoints(): List<GeographyPoint> {
    return this.coordinates.map { GeographyPoint(it.x, it.y) }
}

fun Polygon.toGeographyPolygon(): GeographyPolygon {
    return GeographyPolygon(
        shell = exteriorRing.coordinates.map { coordinate ->
            GeographyPoint(coordinate.x, coordinate.y)
        },
        holes = (0 until this.numInteriorRing).map { i ->
            val curHole: LinearRing = this.getInteriorRingN(i)
            curHole.toGeographyPoints()
        }
    )
}

fun MultiPolygon.toGeographyMultiPolygon(): GeographyMultiPolygon {
    return GeographyMultiPolygon(
        (0 until this.numGeometries).map {i ->
            val curGeometry: Polygon = this.getGeometryN(i) as Polygon
            curGeometry.toGeographyPolygon()
        }
    )
}

fun LineString.toGeographyLineString(): GeographyLineString {
    return GeographyLineString(
        this.coordinates.map { c ->
            GeographyPoint(lon = c.x, lat = c.y)
        }
    )
}
