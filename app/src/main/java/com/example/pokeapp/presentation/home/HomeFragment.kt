package com.example.pokeapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.pokeapp.presentation.favorite.FavoriteScreen
import com.example.pokeapp.presentation.search.SearchScreen
import com.example.pokeapp.presentation.search.SearchViewModel
import com.example.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val searchViewModel by viewModels<SearchViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PokeAppTheme {
                    val parentNavController = findNavController()
                    val homeNavController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(homeNavController) }
                    ) {
                        NavHost(
                            navController = homeNavController,
                            startDestination = BottomNavigationDestination.Search.label
                        ) {
                            composable(BottomNavigationDestination.Search.label) {
                                SearchScreen(
                                    viewModel = searchViewModel,
                                    onClickItem = { id ->
                                        parentNavController.navigate(
                                            HomeFragmentDirections.actionHomeToDetail(id)
                                        )
                                    }
                                )
                            }
                            composable(BottomNavigationDestination.Favorite.label) {
                                FavoriteScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class BottomNavigationDestination(
    val icon: ImageVector,
    val label: String
) {
    Search(Icons.Filled.Home, "Home"),
    Favorite(Icons.Filled.Favorite, "Favorite");
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation {
        BottomNavigationDestination.values().forEachIndexed { index, destination ->
            BottomNavigationItem(
                icon = { Icon(destination.icon, destination.label) },
                label = { Text(destination.label) },
                selected = index == selectedItem,
                onClick = {
                    selectedItem = index
                    navController.navigate(destination.label)
                }
            )
        }
    }
}