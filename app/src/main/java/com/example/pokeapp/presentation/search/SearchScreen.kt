package com.example.pokeapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.pokeapp.entity.PokemonSummaryInfo
import com.example.pokeapp.presentation.common.PokemonIdWithName
import com.example.pokeapp.ui.theme.PokeAppTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    onClickItem: (id: Long) -> Unit
) {
    PokemonList(
        pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems(),
        modifier = modifier,
        onClickListener = onClickItem
    )
}

@Composable
fun PokemonList(
    pagingItems: LazyPagingItems<PokemonSummaryInfo>,
    modifier: Modifier = Modifier,
    onClickListener: (id: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(
                horizontal = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 68.dp),
    ) {
        items(pagingItems) {
            PokemonListItem(
                pokemonInfo = it,
                onClickListener = onClickListener
            )
        }
    }
}

@Composable
fun PokemonListItem(
    pokemonInfo: PokemonSummaryInfo? = null,
    onClickListener: (id: Long) -> Unit = {}
) {
    if (pokemonInfo != null) {
        PokemonIdWithName(
            modifier = Modifier
                .clickable {
                    onClickListener(pokemonInfo.id)
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
            id = pokemonInfo.id,
            name = pokemonInfo.name
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF444444)
@Composable
fun PreviewPokemonListItem() {
    PokeAppTheme {
        PokemonListItem(
            onClickListener = {},
            pokemonInfo = PokemonSummaryInfo.dummy()
        )
    }
}