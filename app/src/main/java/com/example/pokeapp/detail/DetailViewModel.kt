package com.example.pokeapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.infrastructure.PokeRepository
import com.example.pokeapp.search.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
    sealed class State {
        object Initial : State()

        sealed class Initialized : State() {
            abstract val pokemonInfo: PokemonInfo

            data class Ideal(
                override val pokemonInfo: PokemonInfo
            ) : Initialized()

            data class Loading(
                override val pokemonInfo: PokemonInfo
            ) : Initialized()
        }
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    fun initialize(id: Long) {
        viewModelScope.launch {
            val info = repository.getPokemonInfo(id)
            state.postValue(State.Initialized.Ideal(info))
        }
    }
}