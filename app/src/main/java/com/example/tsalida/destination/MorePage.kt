package com.example.tsalida.destination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.composables.MoreCard

@Composable
fun MorePage(navController: NavHostController){
    //onChangeDestination(Destinations.MORE.ordinal)
    val megaphone = painterResource(R.drawable.megaphone)
    val book = painterResource(R.drawable.book)
    val theme = painterResource(R.drawable.colors)
    val support = painterResource(R.drawable.support)
    Column(Modifier.padding(10.dp, 20.dp).fillMaxWidth().fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MoreCard(megaphone, "Responsive\nReading", navController, MoreDestination.READINGLIST.route)
            Spacer(Modifier.width(10.dp))
            MoreCard(book, "End Pages", navController, MoreDestination.ENDLIST.route)
        }
        Spacer(Modifier.height(10.dp))
        Row() {
            MoreCard(theme, "Theme", navController, MoreDestination.THEME.route)
            Spacer(Modifier.width(10.dp))
            MoreCard(support, "Support", navController, MoreDestination.SUPPORT.route)
        }
    }
}