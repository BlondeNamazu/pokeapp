package com.example.pokeapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.rememberImagePainter
import com.example.pokeapp.entity.PokemonDetailInfo
import com.example.pokeapp.entity.PokemonStatInfo
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
                PokemonBaseProfile(
                    pokemonInfo = pokemonInfo,
                    onFavoriteButtonClicked = { id, isFavorite ->
                        viewModel.setFavoriteState(id, isFavorite)
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PokemonTypeList(types = pokemonInfo.types)
                    PokemonStatInfoList(statInfo = pokemonInfo.statInfo)
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

@Composable
fun PokemonBaseProfile(
    pokemonInfo: PokemonDetailInfo,
    onFavoriteButtonClicked: (id: Long, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
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
        PokemonNameWithFavoriteButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            pokemonInfo = pokemonInfo,
            onFavoriteButtonClicked = onFavoriteButtonClicked
        )
    }
}

@Composable
fun PokemonNameWithFavoriteButton(
    pokemonInfo: PokemonDetailInfo,
    onFavoriteButtonClicked: (id: Long, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = pokemonInfo.name,
            fontSize = 64.sp
        )
        IconToggleButton(
            checked = pokemonInfo.isFavorite,
            onCheckedChange = { isFavorite ->
                onFavoriteButtonClicked(pokemonInfo.id, isFavorite)
            }
        ) {
            Icon(
                imageVector = if (pokemonInfo.isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "Favorite button",
                tint = if (pokemonInfo.isFavorite) Color.Magenta else Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPokemonNameWithFavoriteButton() {
    MaterialTheme {
        Column {
            PokemonNameWithFavoriteButton(
                pokemonInfo = PokemonDetailInfo.dummy().copy(isFavorite = true),
                onFavoriteButtonClicked = { _, _ -> }
            )
            PokemonNameWithFavoriteButton(
                pokemonInfo = PokemonDetailInfo.dummy().copy(isFavorite = false),
                onFavoriteButtonClicked = { _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPokemonBaseProfile() {
    MaterialTheme {
        PokemonBaseProfile(
            pokemonInfo = PokemonDetailInfo.dummy(),
            onFavoriteButtonClicked = { _, _ -> }
        )
    }
}

@Composable
fun PokemonTypeList(
    types: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(types) { type ->
            Text(text = type)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPokemonTypeList() {
    MaterialTheme {
        PokemonTypeList(types = PokemonDetailInfo.dummy().types)
    }
}

@Composable
fun PokemonStatInfoList(
    statInfo: PokemonStatInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start
    ) {
        Text("HP: ${statInfo.hp}")
        Text("Attack: ${statInfo.attack}")
        Text("Defense: ${statInfo.defense}")
        Text("SpecialAttack: ${statInfo.specialAttack}")
        Text("SpecialDefense: ${statInfo.specialDefense}")
        Text("Speed: ${statInfo.speed}")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPokemonStatInfoList() {
    MaterialTheme {
        PokemonStatInfoList(statInfo = PokemonDetailInfo.dummy().statInfo)
    }
}
