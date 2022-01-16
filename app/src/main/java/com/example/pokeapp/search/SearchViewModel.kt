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

        sealed class Initialized: State() {
            abstract val pokemonList: List<PokemonInfo>

            data class Ideal(
                override val pokemonList: List<PokemonInfo>
            ) : Initialized()

            data class Loading(
                override val pokemonList: List<PokemonInfo>
            ) : Initialized()
        }
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    fun initialize() {
        viewModelScope.launch {
            state.postValue(State.Initialized.Loading(emptyList()))
            val info = repository.getPokemonList(20, 0)
            state.postValue(State.Initialized.Ideal(info))
        }
    }

    fun refresh(offset: Int) {
        val currentState = state.value as? State.Initialized ?: return
        viewModelScope.launch {
            state.postValue(State.Initialized.Loading(currentState.pokemonList))
            val info = repository.getPokemonList(20, offset.toLong())
            val updatedInfo = currentState.pokemonList.plus(info)
            state.postValue(State.Initialized.Ideal(updatedInfo))
        }
    }
}