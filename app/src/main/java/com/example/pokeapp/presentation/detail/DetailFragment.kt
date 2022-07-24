package com.example.pokeapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.rememberImagePainter
import com.example.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.initialize(args.id)
        return ComposeView(requireContext()).apply {
            setContent {
                PokeAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        PokemonDetailItem(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonDetailItem(viewModel: DetailViewModel) {
    when (val state = viewModel.state.observeAsState().value) {
        is DetailViewModel.State.Initialized.Ideal -> {
            val pokemonInfo = state.pokemonInfo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Pokedex Number: ${pokemonInfo.id}",
                    fontSize = 16.sp
                )
                Image(
                    painter = rememberImagePainter(pokemonInfo.imageUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
                Text(
                    text = pokemonInfo.name,
                    fontSize = 64.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(pokemonInfo.types) { type ->
                            Text(text = type)
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("HP: ${pokemonInfo.statInfo.hp}")
                        Text("Attack: ${pokemonInfo.statInfo.attack}")
                        Text("Defense: ${pokemonInfo.statInfo.defense}")
                        Text("SpecialAttack: ${pokemonInfo.statInfo.specialAttack}")
                        Text("SpecialDefense: ${pokemonInfo.statInfo.specialDefense}")
                        Text("Speed: ${pokemonInfo.statInfo.speed}")
                    }
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
