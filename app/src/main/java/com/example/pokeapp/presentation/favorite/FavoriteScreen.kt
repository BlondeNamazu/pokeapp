package com.example.pokeapp.presentation.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokeapp.entity.PokemonDetailInfo
import com.example.pokeapp.presentation.common.FavoriteButton
import com.example.pokeapp.presentation.common.PokemonIdWithName


@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onClickInfo: (id: Long) -> Unit = {},
    onClickFavorite: (id: Long, isFavorite: Boolean) -> Unit = { _, _ -> }
) {
    when (val state = viewModel.state.observeAsState().value) {
        FavoriteViewModel.State.Initial -> Unit
        is FavoriteViewModel.State.Ideal -> {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Gray),
            ) {
                PokemonList(
                    items = state.pokemonList,
                    onClickInfo = onClickInfo,
                    onClickFavorite = onClickFavorite
                )
            }
        }
    }
}

@Composable
fun PokemonList(
    items: List<PokemonDetailInfo>,
    onClickInfo: (id: Long) -> Unit = {},
    onClickFavorite: (id: Long, isFavorite: Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = Modifier
            .background(
                color = Color.DarkGray,
            )
            .padding(
                horizontal = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 68.dp),
    ) {
        items(items) { info ->
            PokemonListItem(
                info = info,
                onClickInfo = onClickInfo,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@Composable
fun PokemonListItem(
    info: PokemonDetailInfo,
    modifier: Modifier = Modifier,
    onClickInfo: (id: Long) -> Unit = {},
    onClickFavorite: (id: Long, isFavorite: Boolean) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .clickable {
                onClickInfo(info.id)
            }
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = rememberImagePainter(info.imageUrl),
            contentDescription = "pokemon image",
        )
        PokemonIdWithName(
            id = info.id,
            name = info.name
        )
        FavoriteButton(
            checked = info.isFavorite,
            onCheckedChange = { isFavorite ->
                onClickFavorite(info.id, isFavorite)
            }
        )
    }
}

