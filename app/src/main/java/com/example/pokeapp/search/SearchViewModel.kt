package com.example.pokeapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    sealed class State {
        abstract val greeting: String

        object Namazu : State() {
            override val greeting: String get() = "Namazu"
        }
    }

    val state: LiveData<State> by lazy { MutableLiveData(State.Namazu) }
}