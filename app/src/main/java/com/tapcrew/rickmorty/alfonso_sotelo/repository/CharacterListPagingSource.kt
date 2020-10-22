package com.tapcrew.rickmorty.alfonso_sotelo.repository

import androidx.paging.PagingSource
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.NetworkConstants
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.RickMortyService
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects.Character
import java.lang.Exception

class CharacterListPagingSource(
    private val service: RickMortyService,
    private val searchByName: String? = null,
): PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: NetworkConstants.DEFAULT_STARTING_PAGE_INDEX

        return try {
            val response = service.getCharacters(page, searchByName)
            val info = response.info

            LoadResult.Page(
                response.data,
                if (page == NetworkConstants.DEFAULT_STARTING_PAGE_INDEX) null else page - 1,
                if (page == info.pages) null else page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}