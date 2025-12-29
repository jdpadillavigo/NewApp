package com.example.newapp.core.presentation.util

import android.content.Context
import com.example.newapp.R
import com.example.newapp.core.domain.util.NetworkError

fun NetworkError.toMessage(context: Context): String {
    val resId = when(this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}

fun NetworkError.toLogMessage(): String {
    return when(this) {
        NetworkError.REQUEST_TIMEOUT -> "The request timed out."
        NetworkError.TOO_MANY_REQUESTS -> "Oops, it seems like your quota is exceeded."
        NetworkError.NO_INTERNET -> "Couldn't connect to server, please check your internet connection."
        NetworkError.SERVER_ERROR -> "Something went wrong. Please try again later."
        NetworkError.SERIALIZATION -> "Couldn't serialize or deserialize data."
        NetworkError.UNKNOWN -> "Something went wrong. Please try again later."
    }
}