package com.example.pokeapp.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.entity.PokemonDetailInfo
import com.example.pokeapp.infrastructure.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
    sealed class State {
        object Initial : State()
        data class Ideal(
            val pokemonList: List<PokemonDetailInfo>
        ) : State()
    }

    val state: MutableLiveData<State> by lazy { MutableLiveData(State.Initial) }

    fun initialize() {
        viewModelScope.launch {
            repository.getFavoriteFlow()
                .collect { list ->
                    state.postValue(State.Ideal(list))
                }
        }
    }

    fun setFavoriteState(id: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteState(id, isFavorite)
        }
    }
}