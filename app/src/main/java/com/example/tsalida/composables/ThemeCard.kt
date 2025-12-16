package com.example.tsalida.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tsalida.data.DatabaseHelper

@Composable
fun ThemeCard(label: String = "Blue", painter: Painter, themeValue: Int, changeTemp: (Int)->Unit){
    val helper = DatabaseHelper(LocalContext.current)
    Surface(Modifier.border(1.dp, Color.Gray).clickable{helper.updateTheme(helper.writableDatabase, themeValue); changeTemp(themeValue)}) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter, "Blue Color", modifier = Modifier.size(100.dp))
            Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
        }
    }
}