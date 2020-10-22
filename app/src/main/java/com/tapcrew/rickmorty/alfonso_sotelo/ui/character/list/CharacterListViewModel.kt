package com.tapcrew.rickmorty.alfonso_sotelo.ui.character.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tapcrew.rickmorty.alfonso_sotelo.repository.CharacterRepository
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects.Character
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
): ViewModel() {
    var characters: Flow<PagingData<Character>>? = null

    fun loadCharacters(searchByName: String? = null): Flow<PagingData<Character>> {
        val newResult = repository.getCharactersResultStream(searchByName).cachedIn(viewModelScope)
        characters = newResult
        return newResult
    }
}