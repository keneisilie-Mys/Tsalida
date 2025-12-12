package com.example.tsalida.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.destination.CommonFunction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreTopBar(title: String, navController: NavHostController, route: String, pagerState: PagerState){
    val context = LocalContext.current
    TopAppBar(title = {Text(title, fontWeight = FontWeight.SemiBold)},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = { IconButton(onClick = {navController.navigateUp()}){
                                    Icon(painterResource(R.drawable.back_svgrepo_com), "Back Button", modifier = Modifier.size(25.dp))} },
                actions = {IconButton(onClick = {CommonFunction.shareMoreImage(context, pagerState.currentPage+1, route)}) {
                    Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", modifier = Modifier.size(25.dp))
                }})
}