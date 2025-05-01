package com.valeria.mapboxproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.valeria.mapboxproject.R

@Composable
fun LoadingIndicator(modifier: Modifier){
    Box(
        modifier = modifier
            .size(200.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Text(stringResource(R.string.loading_points), modifier = modifier.align(Alignment.Center), color = Color.Black)
    }
}