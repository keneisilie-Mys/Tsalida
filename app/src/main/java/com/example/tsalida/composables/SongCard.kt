package com.example.tsalida.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.data.DatabaseHelper
import com.example.tsalida.destination.CommonFunction

@Composable
fun SongCard(songNo: Int, angamiTitle: String, englishTitle: String, navController: NavHostController){
    val actualSongNo = CommonFunction.fixSongNo(songNo)
    val context = LocalContext.current
    val helper = DatabaseHelper(LocalContext.current)
    var isFav by rememberSaveable { mutableIntStateOf(helper.getFavorite(helper.readableDatabase, actualSongNo)) }
    Surface(Modifier.fillMaxWidth().height(100.dp).clickable{navController.navigate("songs/$actualSongNo")}, shadowElevation = 1.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(10.dp))
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$songNo", modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                Column(Modifier.padding(5.dp)) {
                    Text(angamiTitle, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(3.dp))
                    Text(englishTitle, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {helper.updateFavorite(helper.writableDatabase, actualSongNo); isFav = if(isFav == 0) 1 else 0}) {
                    if(isFav == 0) Icon(painterResource(R.drawable.heart_empty_svgrepo_com),"Heart Icon", Modifier.size(25.dp)) else Icon(painterResource(R.drawable.heart_filled_svgrepo_com), "Heart Icon", Modifier.size(25.dp), tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.width(10.dp))
                IconButton(onClick = { CommonFunction.shareImage(context, actualSongNo)}) {
                    Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
                }
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}