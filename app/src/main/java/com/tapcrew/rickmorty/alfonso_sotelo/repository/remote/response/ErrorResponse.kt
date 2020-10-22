package com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response

import com.google.gson.annotations.SerializedName

class ErrorResponse (
    @field:SerializedName("error") val errorMessage: String?
) {
    override fun toString(): String {
        return errorMessage ?: ""
    }
}