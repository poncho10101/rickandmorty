package com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response

import com.google.gson.annotations.SerializedName
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects.Character

data class CharacterListResponse (
    @field:SerializedName("info") val info: InfoResponse,
    @field:SerializedName("results") val data: List<Character>,
)