package com.example.pokeapp.presentation.search

import androidx.lifecycle.ViewModel
import com.example.pokeapp.infrastructure.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {
    val pagingDataFlow = repository.getPokemonListFlow()
}