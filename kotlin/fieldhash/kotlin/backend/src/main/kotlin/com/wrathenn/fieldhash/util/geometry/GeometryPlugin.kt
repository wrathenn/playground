package com.wrathenn.fieldhash.util.geometry

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.spi.JdbiPlugin

class GeometryPlugin: JdbiPlugin.Singleton() {
    override fun customizeJdbi(jdbi: Jdbi) {
        jdbi.registerArgument(GeographyModelArgumentFactory())
        jdbi.registerColumnMapper(GeographyColumnMapper())
    }
}