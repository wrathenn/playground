package com.wrathenn.fieldhash.util.geography

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.argument.ObjectArgument
import org.jdbi.v3.core.config.ConfigRegistry
import java.sql.Types

/**
 * Binds all GeographyModels
 */
class GeographyModelArgumentFactory: AbstractArgumentFactory<GeographyModel>(Types.OTHER) {
    override fun build(value: GeographyModel, config: ConfigRegistry): Argument {
        return ObjectArgument.of(when(value) {
            is GeographyMultiPolygon -> value.toJts()
            is GeographyPoint -> value.toJts()
            is GeographyPolygon -> value.toJts()
            is GeographyLineString -> value.toJts()
        }, Types.OTHER)
    }
}
