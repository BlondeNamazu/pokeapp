package com.example.pokeapp.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonIdWithName(
    id: Long,
    name: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Start),
            text = "No.${id}",
            style = TextStyle(textDecoration = TextDecoration.Underline),
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp),
            text = name,
            fontSize = 24.sp
        )
    }
}