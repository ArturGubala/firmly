package com.example.firmly.core.presentation.util

import android.content.Context
import com.example.firmly.R
import com.example.firmly.core.domain.util.DataError

fun DataError.toString(context: Context): String {
    val resId = when(this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Network.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.SERVER_ERROR -> R.string.error_unknown
        DataError.Network.SERIALIZATION -> R.string.error_serialization
        DataError.Network.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}
