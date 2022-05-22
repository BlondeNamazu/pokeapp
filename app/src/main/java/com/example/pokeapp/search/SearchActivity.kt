package com.example.pokeapp.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.pokeapp.detail.DetailActivity
import com.example.pokeapp.entity.PokemonSummaryInfo
import com.example.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonList(
                        pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems(),
                        onClickListener = { id ->
                            startActivity(
                                DetailActivity.createIntent(this@SearchActivity, id)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    pagingItems: LazyPagingItems<PokemonSummaryInfo>,
    onClickListener: (id: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .background(
                color = Color.DarkGray,
            )
            .padding(
                horizontal =  12.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
        Column(
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
                )
            ,
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Start),
                text = "No.${pokemonInfo.id}",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp)
                ,
                text = pokemonInfo.name,
                fontSize = 24.sp
            )
        }
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