package com.example.pokeapp.presentation.common

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange
    ) {
        Icon(
            imageVector = if (checked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
            contentDescription = "Favorite button",
            tint = if (checked) Color.Magenta else Color.Gray
        )
    }
}