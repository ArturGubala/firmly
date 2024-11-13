package com.example.firmly.core.data.networking

import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructUrl(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}
