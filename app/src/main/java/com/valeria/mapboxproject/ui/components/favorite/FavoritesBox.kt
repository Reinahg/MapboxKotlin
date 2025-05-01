package com.valeria.mapboxproject.ui.components.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.valeria.mapboxproject.R
import com.valeria.mapboxproject.ui.map.MapNavigator
import com.valeria.mapboxproject.ui.map.MapViewModel
import java.util.Locale

@Composable
fun FavoritesBox(
    modifier: Modifier,
    viewModel: MapViewModel,
    navigator: MapNavigator,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .height(400.dp)
            .clickable { onClick() }
    ) {
        Card(
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.BottomEnd)
                .padding(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    stringResource(R.string.puntos_favoritos),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                val favorites by viewModel.favoritePoints.collectAsState()

                if (favorites.isEmpty()) {
                    Text(stringResource(R.string.no_hay_puntos_guardados), color = Color.Black)
                } else {
                    LazyColumn {
                        items(favorites) { point ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column (modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(2F)){
                                    Text(
                                        stringResource(R.string.name, point.name),
                                        color = Color.Blue
                                    )
                                    Text(
                                        stringResource(
                                            R.string.type,
                                            point.type.replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(
                                                    Locale.ROOT
                                                ) else it.toString()
                                            }),
                                        color = Color.Blue
                                    )
                                }

                                FilledTonalButton(
                                    modifier = Modifier
                                        .weight(1F)
                                        .padding(start = 5.dp),
                                    colors = ButtonColors(
                                        containerColor = Color.Blue,
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.Black,
                                        disabledContentColor = Color.Black
                                    ),
                                    onClick = {
                                        navigator.navigateToSavedPlace(point)
                                    }) {
                                    Text(stringResource(R.string.see))
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}