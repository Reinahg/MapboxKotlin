package com.valeria.mapboxproject.ui.components.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valeria.mapboxproject.ui.components.CircularIconButton
import com.valeria.mapboxproject.ui.map.MapNavigator
import com.valeria.mapboxproject.ui.map.MapViewModel
import com.valeria.mapboxproject.utils.MapStyle
import java.util.Locale

@Composable
fun MapActions(modifier: Modifier, mapNavigator: MapNavigator, viewModel: MapViewModel, onClickFavorite: () -> Unit) {
    var showingFavorite = false
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CircularIconButton(
            icon = if (showingFavorite) Icons.Default.Clear else Icons.Default.Star,
            onClick = {
                showingFavorite = !showingFavorite
                onClickFavorite()
            }
        )
        CircularIconButton(
            icon = Icons.Default.LocationOn,
            onClick = { mapNavigator.navigateToCurrentLocation() }
        )
        MapStyleButton(
            onChangeStyle = { viewModel.changeMapStyle(it) }
        )
    }
}

@Composable
private fun MapStyleButton(
    onChangeStyle: (MapStyle) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        CircularIconButton(
            icon = Icons.Filled.Settings,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MapStyle.values().forEach { style ->
                DropdownMenuItem(
                    text = { Text(style.name.lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
                    onClick = {
                        onChangeStyle(style)
                        expanded = false
                    }
                )
            }
        }
    }
}