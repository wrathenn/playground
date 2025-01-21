package com.wrathenn.fieldhash.desktop.service

import com.wrathenn.fieldhash.api.model.NamedDto
import io.ktor.client.call.*
import io.ktor.http.*

object PartitionChainsHttp {
    suspend fun getAll() = Http.request(
        method = HttpMethod.Get,
        path = "api/partitions/chains",
    ).body<List<NamedDto<Long>>>()
}