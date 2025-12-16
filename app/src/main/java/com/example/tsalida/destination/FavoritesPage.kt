package com.example.tsalida.destination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tsalida.composables.SongCard
import com.example.tsalida.viewModels.FavoriteViewModel
import com.example.tsalida.viewModels.factory.FavoriteViewModelFactory

@Composable
fun FavoritesPage(navHostController: NavHostController, onChangeDestination: (Int)->Unit){
    onChangeDestination(Destinations.FAVORITES.ordinal)
    val context = LocalContext.current
    val viewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModelFactory(context.applicationContext))
    val favSongs by viewModel.favList.collectAsStateWithLifecycle()

    if(favSongs.isEmpty()){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(100.dp))
            Text(text = "No Favorites Added Yet", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
            Button(onClick = {navHostController.navigate(Destinations.LIST.route)}) {
                Text("Tap to Add more")
            }
        }
    }
    LazyColumn(modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)) {
        items(items = favSongs, key = {it.songNo}){
            SongCard(it.songNo, it.angamiTitle, it.englishTitle, navHostController)
        }
    }
}