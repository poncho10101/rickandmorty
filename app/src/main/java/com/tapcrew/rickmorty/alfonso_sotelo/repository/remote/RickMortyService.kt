package com.tapcrew.rickmorty.alfonso_sotelo.repository.remote

import com.haroldadmin.cnradapter.NetworkResponse
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response.CharacterListResponse
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.response.ErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

typealias GenericResponse<R> = NetworkResponse<R, ErrorResponse>

interface RickMortyService {

    @GET(NetworkConstants.CHARACTERS_METHOD)
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") searchByName: String? = null
    ): CharacterListResponse


}