package com.example.tsalida.destination.more

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.tsalida.composables.MoreListTopBar
import com.example.tsalida.composables.MoreTopBar
import com.example.tsalida.composables.PageCard
import com.example.tsalida.data.SongList
import com.example.tsalida.destination.MoreDestination
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage



@Composable
fun ReadingListPage(navController: NavHostController){
    Scaffold(topBar = { MoreListTopBar(MoreDestination.READINGLIST.label, navController) }, containerColor = Color.White) { padding->
        LazyColumn(Modifier.padding(top = padding.calculateTopPadding())) {
            items(items = SongList.resPages, key = {it.pageNo}){
                PageCard(it.pageNo, it.title, navController, MoreDestination.READINGLIST.route)
            }
        }
    }
}


@Composable
fun ReadingPage(navController: NavHostController, startPage: Int = 0){
    val pagerState = rememberPagerState(pageCount = {32})
    LaunchedEffect(startPage) {
        pagerState.scrollToPage(startPage)
    }
    Scaffold(topBar = {MoreTopBar(MoreDestination.READING.label, navController,
        MoreDestination.READING.route, pagerState)}, containerColor = Color.White) { innerPadding->
        HorizontalPager(pagerState) {page->
            ZoomableAsyncImage(String.format("file:///android_asset/Responsive Reading/prelude%d.jpg", page+1), "Responsive Reading Image", Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()))
        }
    }
}