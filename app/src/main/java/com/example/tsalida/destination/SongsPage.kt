package com.example.tsalida.destination

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tsalida.R
import com.example.tsalida.data.DatabaseHelper
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsBar(pagerState: PagerState){
    var isFav by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)
    isFav = databaseHelper.getFavorite(databaseHelper.readableDatabase, pagerState.currentPage + 1)
    LaunchedEffect(pagerState.currentPage) {
        isFav = databaseHelper.getFavorite(databaseHelper.readableDatabase, pagerState.currentPage + 1)
    }
    TopAppBar(windowInsets = TopAppBarDefaults.windowInsets, title = {Text("")}, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        actions = {IconButton(onClick = {databaseHelper.updateFavorite(databaseHelper.writableDatabase, pagerState.currentPage+1); isFav =  if(isFav == 0) 1 else 0}) {
            if (isFav == 0) Icon(painterResource(R.drawable.heart_empty_svgrepo_com), "Heart Icon", Modifier.size(25.dp)) else Icon(painterResource(R.drawable.heart_filled_svgrepo_com), "Heart Icon", Modifier.size(25.dp), tint = Color.Unspecified)
        }
            IconButton(onClick = { CommonFunction.shareImage(context, pagerState.currentPage+1)}) {
                Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
            }})
}



@Composable
fun SongsPage(startPage: Int = 0, onChangeDestination: (Int)->Unit){
    onChangeDestination(Destinations.SONGS.ordinal)
    val pageState = rememberPagerState(pageCount = {433})
    LaunchedEffect(startPage) {
        pageState.scrollToPage(startPage)
    }

    Scaffold(topBar = {SongsBar(pageState)}, containerColor = Color.White) { innerPadding->
        HorizontalPager(state = pageState, modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())) { page->
            ZoomableAsyncImage(String.format(Locale.US, "file:///android_asset/Hymns/p%d.jpg", page+1), "Image")
        }
    }
}


