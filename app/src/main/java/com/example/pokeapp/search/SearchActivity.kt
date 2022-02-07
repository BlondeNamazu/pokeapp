package com.example.pokeapp.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokeapp.detail.DetailActivity
import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.ui.theme.PokeAppTheme
import com.example.pokeapp.util.OnAppearLastItem
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
                        onClickListener = { id: Long ->
                            startActivity(DetailActivity.createIntent(this, id))
                        },
                        viewModel
                    )
                }
            }
        }
        viewModel.initialize()
    }
}

@Composable
fun PokemonList(onClickListener: (id: Long) -> Unit, viewModel: SearchViewModel) {
    when (val state = viewModel.state.observeAsState().value) {
        is SearchViewModel.State.Initial -> Text("Loading Pokemon Info...")
        is SearchViewModel.State.Initialized -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState().apply {
                    OnAppearLastItem(onAppearLastItem = { offset -> viewModel.refresh(offset) })
                }
            ) {
                items(state.pokemonList) { pokemonInfo ->
                    PokemonListItem(onClickListener = onClickListener, pokemonInfo = pokemonInfo)
                }
            }
            if (state is SearchViewModel.State.Initialized.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(onClickListener: (id: Long) -> Unit, pokemonInfo: PokemonInfo) {
    Row(
        modifier = Modifier
            .clickable {
                onClickListener(pokemonInfo.id)
            }
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(pokemonInfo.imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(
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
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonInfo.types) { type ->
                    Text(
                        text = type,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokeAppTheme {
        PokemonListItem(
            onClickListener = {},
            pokemonInfo = PokemonInfo.dummy()
        )
    }
}