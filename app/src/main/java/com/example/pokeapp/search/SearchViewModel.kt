package com.example.pokeapp.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.entity.PagingInfo
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
        abstract val pagingInfo: PagingInfo?

        object Initial : State() {
            override val pagingInfo: PagingInfo? get() = null
        }

        sealed class Initialized : State() {
            abstract val pokemonList: List<PokemonInfo>

            data class Ideal(
                override val pokemonList: List<PokemonInfo>,
                override val pagingInfo: PagingInfo?
            ) : Initialized()

            data class Loading(
                override val pokemonList: List<PokemonInfo>,
                override val pagingInfo: PagingInfo?
            ) : Initialized()
        }
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    fun initialize() {
        viewModelScope.launch {
            val initialPagingInfo = PagingInfo(offset = 0, limit = 100)
            state.postValue(State.Initialized.Loading(emptyList(), initialPagingInfo))
            val result = repository.getPokemonList(initialPagingInfo)
            state.postValue(
                State.Initialized.Ideal(
                    pokemonList = result.pokemonInfoList,
                    pagingInfo = result.pagingInfo
                )
            )
        }
    }

    fun refresh() {
        val currentState = state.value as? State.Initialized ?: return
        viewModelScope.launch {
            val pagingInfo = currentState.pagingInfo
            if (pagingInfo != null) {
                state.postValue(
                    State.Initialized.Loading(
                        pokemonList = currentState.pokemonList,
                        pagingInfo = currentState.pagingInfo
                    )
                )
                val result = repository.getPokemonList(pagingInfo)
                val updatedInfoList = currentState.pokemonList.plus(result.pokemonInfoList)
                state.postValue(State.Initialized.Ideal(updatedInfoList, result.pagingInfo))
            }
        }
    }
}