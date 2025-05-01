package com.valeria.mapboxproject.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valeria.mapboxproject.ui.components.GifEarth

@Composable
fun MainScreen(
    goToMap: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize().background(Color.Black),
        containerColor = Color.Black) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mapas con mapbox",
                color = Color.Cyan,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            GifEarth(modifier = Modifier.padding(vertical = 25.dp))
            Text(
                text = "Hecho por Valeria Henao",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            )
            Button(
                onClick = { goToMap() },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
            ) {
                Text(
                    text = "Ir al mapa",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}