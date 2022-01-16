package com.example.pokeapp.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import coil.compose.rememberImagePainter
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
                        viewModel
                    )
                }
            }
        }
        viewModel.initialize()
    }
}

@Composable
fun PokemonList(viewModel: SearchViewModel) {
    when (val state = viewModel.state.observeAsState().value) {
        is SearchViewModel.State.Initial -> Text("Loading Pokemon Info...")
        is SearchViewModel.State.Initialized -> {
            LazyColumn(
                state = rememberLazyListState().apply {
                    OnAppearLastItem(onAppearLastItem = { offset -> viewModel.refresh(offset) })
                }
            ) {
                items(state.pokemonList) { pokemonInfo ->
                    PokemonListItem(pokemonInfo = pokemonInfo)
                }
            }
            if (state is SearchViewModel.State.Initialized.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(pokemonInfo: PokemonInfo) {
    Row {
        Image(
            painter = rememberImagePainter(pokemonInfo.imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Column {
            Text(text = pokemonInfo.name)
            Text(text = pokemonInfo.types.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListItemPreview() {
    PokeAppTheme {
        PokemonListItem(
            pokemonInfo = PokemonInfo.dummy()
        )
    }
}