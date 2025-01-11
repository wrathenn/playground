package com.wrathenn.fieldhash.shared

sealed interface GeometryModel

/**
 * Uses lon and lat instead of X and Y coordinates
 */
data class GeometryPoint(
    val x: Double,
    val y: Double
): GeometryModel

/**
 * Polyline
 */
data class GeometryLineString(
    val points: List<GeometryPoint>
): GeometryModel

/**
 * Each list of points should have its first point as its last point (e.g. [[A, B, ..., A]])
 *
 * See [Difference between Polygon and MultiPolygon](https://gis.stackexchange.com/questions/225368/understanding-difference-between-polygon-and-multipolygon-for-shapefiles-in-qgis)
 * to learn about its constraints.
 * @param shell outer polygon
 * @param holes inner polygons that are holes in the shell
 */
data class GeometryPolygon(
    val shell: List<GeometryPoint>,
    val holes: List<List<GeometryPoint>> = emptyList()
): GeometryModel {
    constructor(nwX: Double, seX: Double, nwY: Double, seY: Double) : this(
        shell = listOf(
            GeometryPoint(nwX, nwY),
            GeometryPoint(nwX, seY),
            GeometryPoint(seX, seY),
            GeometryPoint(seX, nwY),
            GeometryPoint(nwX, nwY)
        )
    )
}

/**
 * MultiPolygon model
 *
 * See [Difference between Polygon and MultiPolygon](https://gis.stackexchange.com/questions/225368/understanding-difference-between-polygon-and-multipolygon-for-shapefiles-in-qgis)
 * to learn about its constraints.
 * @param polygons list of polygons that form a multipolygon
 */
data class GeometryMultiPolygon(
    val polygons: List<GeometryPolygon>
): GeometryModel
