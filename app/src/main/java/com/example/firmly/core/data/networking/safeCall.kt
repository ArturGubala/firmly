package com.example.firmly.core.data.networking

import com.example.firmly.core.domain.util.DataError
import com.example.firmly.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch(e: SerializationException) {
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch(e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}
