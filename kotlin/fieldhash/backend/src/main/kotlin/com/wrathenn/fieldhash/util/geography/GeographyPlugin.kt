package com.wrathenn.fieldhash.util.geography

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.spi.JdbiPlugin

class GeographyPlugin: JdbiPlugin.Singleton() {
    override fun customizeJdbi(jdbi: Jdbi) {
        jdbi.registerArgument(GeographyModelArgumentFactory())
        jdbi.registerColumnMapper(GeographyColumnMapper())
    }
}