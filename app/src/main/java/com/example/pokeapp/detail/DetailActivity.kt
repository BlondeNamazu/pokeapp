package com.example.pokeapp.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgs
import coil.compose.rememberImagePainter
import com.example.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val args: DetailActivityArgs by navArgs()

    companion object {
        fun createIntent(context: Context, id: Long): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtras(DetailActivityArgs(id).toBundle())
            }
        }
    }

    private val viewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonDetailItem(viewModel = viewModel)
                }
            }
        }
        viewModel.initialize(args.id)
    }

}

@Composable
fun PokemonDetailItem(viewModel: DetailViewModel) {
    when (val state = viewModel.state.observeAsState().value) {
        is DetailViewModel.State.Initialized.Ideal -> {
            val pokemonInfo = state.pokemonInfo
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
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
