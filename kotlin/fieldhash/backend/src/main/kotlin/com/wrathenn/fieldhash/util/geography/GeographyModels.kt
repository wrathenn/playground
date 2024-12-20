package com.wrathenn.fieldhash.util.geography

sealed interface GeographyModel

/**
 * Uses lon and lat instead of X and Y coordinates
 */
data class GeographyPoint(
    val lon: Double,
    val lat: Double
): GeographyModel

/**
 * Polyline
 */
data class GeographyLineString(
    val points: List<GeographyPoint>
): GeographyModel

/**
 * Each list of points should have its first point as its last point (e.g. [[A, B, ..., A]])
 *
 * See [Difference between Polygon and MultiPolygon](https://gis.stackexchange.com/questions/225368/understanding-difference-between-polygon-and-multipolygon-for-shapefiles-in-qgis)
 * to learn about its constraints.
 * @param shell outer polygon
 * @param holes inner polygons that are holes in the shell
 */
data class GeographyPolygon(
    val shell: List<GeographyPoint>,
    val holes: List<List<GeographyPoint>> = emptyList()
): GeographyModel {
    constructor(nwX: Double, seX: Double, nwY: Double, seY: Double) : this(
        shell = listOf(
            GeographyPoint(nwX, nwY),
            GeographyPoint(nwX, seY),
            GeographyPoint(seX, seY),
            GeographyPoint(seX, nwY),
            GeographyPoint(nwX, nwY)
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
data class GeographyMultiPolygon(
    val polygons: List<GeographyPolygon>
): GeographyModel
