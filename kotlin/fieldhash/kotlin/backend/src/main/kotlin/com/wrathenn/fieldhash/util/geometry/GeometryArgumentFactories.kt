package com.wrathenn.fieldhash.util.geometry

import com.wrathenn.fieldhash.shared.*
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.argument.ObjectArgument
import org.jdbi.v3.core.config.ConfigRegistry
import java.sql.Types

/**
 * Binds all GeographyModels
 */
class GeographyModelArgumentFactory: AbstractArgumentFactory<GeometryModel>(Types.OTHER) {
    override fun build(value: GeometryModel, config: ConfigRegistry): Argument {
        return ObjectArgument.of(when(value) {
            is GeometryMultiPolygon -> value.toJts()
            is GeometryPoint -> value.toJts()
            is GeometryPolygon -> value.toJts()
            is GeometryLineString -> value.toJts()
        }, Types.OTHER)
    }
}
