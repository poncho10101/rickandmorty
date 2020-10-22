package com.tapcrew.rickmorty.alfonso_sotelo.utils

import android.content.Context
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.gson.Gson
import com.tapcrew.rickmorty.alfonso_sotelo.R
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

fun LoadState.Error.getMessageString(ctx: Context): String? {
    return this.getMessageString(ctx, ErrorResponse::class.java)
}


fun LoadState.Error.getMessageString(ctx: Context, errorClass: Class<*>?): String? {
    return when(this.error) {
        is IOException -> ctx.getString(R.string.error_network)
        is HttpException -> {
            val error = (this.error as HttpException).response()?.errorBody()
            val errorMessage: String? = when {
                error == null -> null
                error.contentLength() == 0L -> null
                else -> try {
                    errorClass?.let {
                        Gson().fromJson(error.string(), errorClass).toString()
                    }
                } catch (e: Exception) {
                    null
                }
            }

            errorMessage ?: ctx.getString(R.string.error_unknown)
        }
        else -> ctx.getString(R.string.error_unknown)
    }
}

fun CombinedLoadStates.asError(): LoadState.Error? {
    return this.source.append as? LoadState.Error
        ?: this.source.prepend as? LoadState.Error
        ?: this.append as? LoadState.Error
        ?: this.prepend as? LoadState.Error
}

fun CombinedLoadStates.getMessageString(ctx: Context): String? {
    return this.asError()?.getMessageString(ctx)
}
