package com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response

import com.google.gson.annotations.SerializedName

data class InfoResponse (
    @field:SerializedName("count") val count: Int,
    @field:SerializedName("pages") val pages: Int,
    @field:SerializedName("next") val nextPage: String?,
    @field:SerializedName("prev") val prevPage: String?,
)