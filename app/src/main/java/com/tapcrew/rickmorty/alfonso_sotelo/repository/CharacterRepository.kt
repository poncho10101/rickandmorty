package com.tapcrew.rickmorty.alfonso_sotelo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.NetworkConstants
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.RickMortyService
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val service: RickMortyService
) {

    fun getCharactersResultStream(searchByName: String? = null): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConstants.DEFAULT_PAGE_SIZE,
                initialLoadSize = NetworkConstants.DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = { CharacterListPagingSource(service, searchByName) }
        ).flow
    }

}