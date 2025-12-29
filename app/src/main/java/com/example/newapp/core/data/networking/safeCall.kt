package com.example.newapp.core.data.networking

import android.util.Log
import com.example.newapp.core.domain.util.NetworkError
import com.example.newapp.core.presentation.util.toLogMessage
import com.example.newapp.news.data.networking.dto.NewResponseDto
import com.example.newapp.news.domain.NewResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend fun safeCall(
    execute: suspend () -> HttpResponse,
    onSuccess: (NewResponseDto) -> NewResponse
): NewResponse {
    val tag = "safeCall"
    return try {
        val response = execute()
        when (response.status.value) {
            in 200..299 -> {
                try {
                    val responseBody = response.body<NewResponseDto>()
                    onSuccess(responseBody)
                } catch (e: NoTransformationFoundException) {
                    val message = NetworkError.SERIALIZATION.toLogMessage()
                    Log.e(tag, message, e)
                    NewResponse(status = "error", message = message)
                }
            }
            408 -> {
                val message = NetworkError.REQUEST_TIMEOUT.toLogMessage()
                Log.w(tag, "HTTP 408: $message")
                NewResponse(status = "error", message = message)
            }
            429 -> {
                val message = NetworkError.TOO_MANY_REQUESTS.toLogMessage()
                Log.w(tag, "HTTP 429: $message")
                NewResponse(status = "error", message = message)
            }
            in 500..599 -> {
                val message = NetworkError.SERVER_ERROR.toLogMessage()
                Log.e(tag, "HTTP ${response.status.value}: $message")
                NewResponse(status = "error", message = message)
            }
            else -> {
                val message = NetworkError.UNKNOWN.toLogMessage()
                Log.e(tag, message)
                NewResponse(status = "error", message = message)
            }
        }
    } catch (e: UnresolvedAddressException) {
        val message = NetworkError.NO_INTERNET.toLogMessage()
        Log.e(tag, message, e)
        NewResponse(status = "error", message = message)
    } catch (e: SerializationException) {
        val message = NetworkError.SERIALIZATION.toLogMessage()
        Log.e(tag, message, e)
        NewResponse(status = "error", message = message)
    } catch (e: Exception) {
        val message = NetworkError.UNKNOWN.toLogMessage()
        Log.e(tag, message, e)
        NewResponse(status = "error", message = message)
    }
}