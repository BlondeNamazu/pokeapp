package com.example.pokeapp.presentation.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokeapp.entity.PokemonDetailInfo


@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel
) {
    when (val state = viewModel.state.observeAsState().value) {
        FavoriteViewModel.State.Initial -> Unit
        is FavoriteViewModel.State.Ideal -> {
            LazyColumn {
                items(state.pokemonList) { info ->
                    PokemonListItem(info = info)
                }
            }
        }

    }
}

@Composable
fun PokemonListItem(
    info: PokemonDetailInfo
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = rememberImagePainter(info.imageUrl),
            contentDescription = "pokemon image",
        )
        Text(
            text = info.name
        )
    }
}