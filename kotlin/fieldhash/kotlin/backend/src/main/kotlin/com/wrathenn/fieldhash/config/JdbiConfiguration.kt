package com.wrathenn.fieldhash.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.wrathenn.fieldhash.util.geometry.GeometryPlugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.statement.Slf4JSqlLogger
import org.jdbi.v3.jackson2.Jackson2Config
import org.jdbi.v3.jackson2.Jackson2Plugin
import org.jdbi.v3.json.JsonPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
class JdbiConfiguration(
    private val dataSource: DataSource,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun jdbi(): Jdbi {
        val ds = TransactionAwareDataSourceProxy(dataSource)

        return Jdbi.create(ds)
            .installPlugin(PostgresPlugin())
            .installPlugin(KotlinPlugin())
            .installPlugin(Jackson2Plugin())
            .installPlugin(JsonPlugin())
            .installPlugin(GeometryPlugin())
            .setSqlLogger(Slf4JSqlLogger())
            .also {
                it.getConfig(Jackson2Config::class.java).mapper = objectMapper
            }
    }
}
