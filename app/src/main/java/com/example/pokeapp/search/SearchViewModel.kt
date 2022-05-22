package com.example.pokeapp.search

import androidx.lifecycle.ViewModel
import com.example.pokeapp.infrastructure.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
//    sealed class State {
//
//        object Initial : State()
//
//        sealed class Initialized : State() {
//            abstract val pokemonListFlow: Flow<PagingData<PokemonInfo.Summary>>
//
//            data class Ideal(
//                override val pokemonListFlow: Flow<PagingData<PokemonInfo.Summary>>
//            ) : Initialized()
//
//            data class Loading(
//                override val pokemonListFlow: Flow<PagingData<PokemonInfo.Summary>>
//            ) : Initialized()
//        }
//    }

//    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    val pagingDataFlow = repository.getPokemonListFlow()

//    fun initialize() {
//        viewModelScope.launch {
//            val initialPagingInfo = PagingInfo(offset = 0, limit = 100)
//            state.postValue(State.Initialized.Loading(emptyList(), initialPagingInfo))
//            val result = repository.getPokemonList(initialPagingInfo)
//            state.postValue(
//                State.Initialized.Ideal(
//                    pokemonList = result.pokemonInfoList,
//                    pagingInfo = result.pagingInfo
//                )
//            )
//        }
//    }

//    fun refresh() {
//        val currentState = state.value as? State.Initialized ?: return
//        viewModelScope.launch {
//            val pagingInfo = currentState.pagingInfo
//            if (pagingInfo != null) {
//                state.postValue(
//                    State.Initialized.Loading(
//                        pokemonList = currentState.pokemonList,
//                        pagingInfo = currentState.pagingInfo
//                    )
//                )
//                val result = repository.getPokemonList(pagingInfo)
//                val updatedInfoList = currentState.pokemonList.plus(result.pokemonInfoList)
//                state.postValue(State.Initialized.Ideal(updatedInfoList, result.pagingInfo))
//            }
//        }
//    }
}