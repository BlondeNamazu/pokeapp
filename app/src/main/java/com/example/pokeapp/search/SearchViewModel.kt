package com.example.pokeapp.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.infrastructure.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
    sealed class State {
        abstract val name: String

        object Namazu : State() {
            override val name: String get() = "Namazu"
        }

        data class PokemonName(
            override val name: String
        ) : State()
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Namazu) }

    fun initialize() {
        viewModelScope.launch {
            val info = repository.getPokemonList(20, 0)
            state.postValue(State.PokemonName(info.first().name))
        }
    }
}