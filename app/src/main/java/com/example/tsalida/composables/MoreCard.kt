package com.example.tsalida.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R

@Composable
fun MoreCard(painter: Painter = painterResource(R.drawable.megaphone), title: String = "Responsive\n Reading", navController: NavHostController, route: String){
    Surface(shape = RoundedCornerShape(10.dp), modifier = Modifier.height(250.dp).width(150.dp).clickable{navController.navigate(route)}, shadowElevation = 2.dp) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(10.dp))
            Image(painter, "$title Icon", Modifier.padding(20.dp, 30.dp).size(100.dp))
            Spacer(Modifier.height(20.dp))
            Text(title,  textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(15.dp))
        }
    }
}