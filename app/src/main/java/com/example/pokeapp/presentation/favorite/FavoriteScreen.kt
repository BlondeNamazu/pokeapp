package com.example.pokeapp.presentation.favorite

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun FavoriteScreen() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        text = "Favorite",
    )
}