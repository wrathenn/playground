package com.wrathenn.fieldhash.desktop.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

private const val TAG = "HTTP_CLIENT_SERVICE"

object ApiConst {
    const val DEFAULT_API_TIMEOUT = 60_000L
}

object Http {
    val logger = LoggerFactory.getLogger(this::class.java)
    val baseUrl = "localhost:8080/"

    @OptIn(ExperimentalSerializationApi::class)
    fun jsonObject() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    val client: HttpClient = HttpClient(CIO) {
        followRedirects = false
        install(HttpTimeout) {
            requestTimeoutMillis = ApiConst.DEFAULT_API_TIMEOUT
            connectTimeoutMillis = ApiConst.DEFAULT_API_TIMEOUT
            socketTimeoutMillis = ApiConst.DEFAULT_API_TIMEOUT
        }
        install(Logging) {
            level = LogLevel.INFO
        }
        install(ContentNegotiation) {
            json(jsonObject())
        }
    }

    suspend fun request(
        method: HttpMethod,
        path: String,
        contentType: ContentType = ContentType.Application.Json,
        headers: Map<String, String> = emptyMap(),
        listener: ((bytesSentTotal: Long, contentLength: Long) -> Unit)? = null,
        data: Any? = null,
    ): HttpResponse {
        val headersWithAuth = headers.toMutableMap()

        return request(
            method = method,
            url = Url("${baseUrl}/${path}"),
            contentType = contentType,
            headers = headersWithAuth,
            listener = listener,
            data = data,
            httpClient = client,
        )
    }

    private suspend fun request(
        method: HttpMethod,
        url: Url,
        contentType: ContentType,
        headers: MutableMap<String, String>,
        parameters: Map<Any, Any> = emptyMap(),
        data: Any?,
        httpClient: HttpClient,
        listener: ((bytesSentTotal: Long, contentLength: Long) -> Unit)? = null,
    ): HttpResponse = HttpStatement(
        builder = HttpRequestBuilder().also {
            it.method = method
            it.url.takeFrom(url)
            it.url {
                this.takeFrom(url)
                parameters.forEach { item ->
                    this.parameters.append(item.key.toString(), item.value.toString())
                }
            }
            it.contentType(contentType)
            it.headers {
                headers.forEach { (k, v) ->
                    append(k, v)
                }
            }

            if (listener != null) {
                it.onDownload { bytesSentTotal, contentLength ->
                    listener(bytesSentTotal, contentLength ?: 0)
                }
            }

            data?.also { data -> it.setBody(data) }
        },
        client = httpClient
    ).execute()
}