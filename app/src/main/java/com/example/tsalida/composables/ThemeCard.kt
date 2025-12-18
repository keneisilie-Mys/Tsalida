package com.example.tsalida.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tsalida.R
import com.example.tsalida.data.DatabaseHelper

@Composable
fun ThemeCard(label: String, painter: Painter, themeValue: Int, themeValueVM: Int, changeTemp: (Int)->Unit){
    val helper = DatabaseHelper(LocalContext.current)
    Surface(Modifier.border(1.dp, Color.Gray).clickable{helper.updateTheme(helper.writableDatabase, themeValue); changeTemp(themeValue)}) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.height(100.dp).width(100.dp)){
                Image(painter, "Color")
                if (themeValue == themeValueVM)Image(painterResource(R.drawable.correct_svgrepo_com), "Correct", modifier = Modifier.padding(20.dp))
            }
            Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))
        }
    }
}