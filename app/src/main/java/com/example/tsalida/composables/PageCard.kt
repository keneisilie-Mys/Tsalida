package com.example.tsalida.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.destination.CommonFunction


@Composable
fun PageCard(pageNo: Int, title: String, navController: NavHostController, route: String){
    val context = LocalContext.current
    Surface(modifier = Modifier.height(100.dp).clickable{if (route == "readinglist") navController.navigate("reading/$pageNo") else navController.navigate("end/$pageNo")}, shadowElevation = 2.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(10.dp))
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text("$pageNo", modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
            }
            IconButton(onClick = { CommonFunction.shareMoreImage(context, pageNo, route)}) {
                Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}
