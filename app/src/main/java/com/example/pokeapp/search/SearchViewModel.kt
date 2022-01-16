package com.example.pokeapp.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.infrastructure.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
    sealed class State {
        object Initial : State()

        data class Initialized(
            val pokemonList: List<PokemonInfo>
        ) : State()
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    fun initialize() {
        viewModelScope.launch {
            val info = repository.getPokemonList(20, 0)
            state.postValue(State.Initialized(info))
        }
    }

    fun refresh(offset: Int) {
        val currentState = state.value as? State.Initialized ?: return
        viewModelScope.launch {
            val info = repository.getPokemonList(20, offset.toLong())
            val updatedInfo = currentState.pokemonList.plus(info)
            state.postValue(State.Initialized(updatedInfo))
        }
    }
}