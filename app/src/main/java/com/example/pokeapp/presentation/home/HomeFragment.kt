package com.example.pokeapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.pokeapp.presentation.favorite.FavoriteScreen
import com.example.pokeapp.presentation.favorite.FavoriteViewModel
import com.example.pokeapp.presentation.search.SearchScreen
import com.example.pokeapp.presentation.search.SearchViewModel
import com.example.pokeapp.ui.theme.PokeAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val searchViewModel by viewModels<SearchViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteViewModel.initialize()
        return ComposeView(requireContext()).apply {
            setContent {
                PokeAppTheme {
                    val parentNavController = findNavController()
                    val homeNavController = rememberAnimatedNavController()
                    val modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.DarkGray)
                    Scaffold(
                        bottomBar = { BottomNavigationBar(homeNavController) }
                    ) {
                        AnimatedNavHost(
                            navController = homeNavController,
                            startDestination = BottomNavigationDestination.Search.label
                        ) {
                            composable(
                                BottomNavigationDestination.Search.label,
                                enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { -it }
                                    )
                                },
                                exitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { -it }
                                    )
                                }
                            ) {
                                SearchScreen(
                                    viewModel = searchViewModel,
                                    modifier = modifier,
                                    onClickItem = { id ->
                                        parentNavController.navigate(
                                            HomeFragmentDirections.actionHomeToDetail(id)
                                        )
                                    }
                                )
                            }
                            composable(
                                BottomNavigationDestination.Favorite.label,
                                enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { it }
                                    )
                                },
                                exitTransition = {
                                    slideOutHorizontally(
                                        targetOffsetX = { it }
                                    )
                                }
                            ) {
                                FavoriteScreen(
                                    viewModel = favoriteViewModel,
                                    modifier = modifier,
                                    onClickInfo = { id ->
                                        parentNavController.navigate(
                                            HomeFragmentDirections.actionHomeToDetail(id)
                                        )
                                    },
                                    onClickFavorite = { id, isFavorite ->
                                        favoriteViewModel.setFavoriteState(id, isFavorite)
                                    }
                                )
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