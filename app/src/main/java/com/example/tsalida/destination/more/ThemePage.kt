package com.example.tsalida.destination.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tsalida.R
import com.example.tsalida.composables.ThemeCard

@Composable
fun ThemePage(changeTemp:(Int)->Unit){
    val bluePainter = painterResource(R.drawable.blue)
    val greenPainter = painterResource(R.drawable.green)
    val yellowPainter = painterResource(R.drawable.yellow)
    val defaultPainter = painterResource(R.drawable.defaultcolor)
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.height(600.dp).fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(Modifier.height(60.dp))
        Text("Pick Your Theme", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ThemeCard("Default", defaultPainter, 1, changeTemp)
            ThemeCard("Blue", bluePainter, 2,changeTemp)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ThemeCard("Green", greenPainter, 3, changeTemp)
            ThemeCard("Yellow", yellowPainter, 4, changeTemp)
        }
    }
}