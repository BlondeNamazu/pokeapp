package com.example.pokeapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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
                    SearchScreen(
                        viewModel = searchViewModel,
                        onClickItem = { id ->
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeToDetail(id)
                            )
                        }
                    )
                }
            }
        }
    }
}
