package com.example.pokeapp.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn {
                        items(pagingItems) {
                            PokemonListItem(
                                onClickListener = { id ->
                                    startActivity(
                                        DetailActivity.createIntent(this@SearchActivity, id)
                                    )
                                },
                                pokemonInfo = it
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(
    onClickListener: (id: Long) -> Unit,
    pokemonInfo: PokemonSummaryInfo?
) {
    if(pokemonInfo!=null){
        Row(
            modifier = Modifier
                .clickable {
                    onClickListener(pokemonInfo.id)
                }
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.alignByBaseline(),
                text = "No.${pokemonInfo.id}",
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.alignByBaseline(),
                text = pokemonInfo.name,
                fontSize = 24.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokeAppTheme {
        PokemonListItem(
            onClickListener = {},
            pokemonInfo = PokemonSummaryInfo.dummy()
        )
    }
}