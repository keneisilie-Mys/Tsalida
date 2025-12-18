package com.example.tsalida.destination.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.composables.MoreListTopBar
import com.example.tsalida.composables.ThemeCard

@Composable
fun ThemePage(navController: NavHostController,themeValueVM: Int, changeTemp:(Int)->Unit){
    val bluePainter = painterResource(R.drawable.blue)
    val greenPainter = painterResource(R.drawable.green)
    val yellowPainter = painterResource(R.drawable.yellow)
    val defaultPainter = painterResource(R.drawable.defaultcolor)
    val scrollState = rememberScrollState()
    Scaffold(topBar = { MoreListTopBar("", navController) }) { contentPadding->
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.height(600.dp).fillMaxWidth().padding(top = contentPadding.calculateTopPadding()).verticalScroll(scrollState), verticalArrangement = Arrangement.SpaceBetween) {
            Text("Pick Your Theme", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                ThemeCard("Default", defaultPainter, 1,themeValueVM, changeTemp)
                ThemeCard("Blue", bluePainter, 2,themeValueVM, changeTemp)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                ThemeCard("Green", greenPainter, 3, themeValueVM, changeTemp)
                ThemeCard("Yellow", yellowPainter, 4, themeValueVM, changeTemp)
            }
        }
    }
}