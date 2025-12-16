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
import androidx.navigation.NavHostController
import com.example.tsalida.composables.MoreListTopBar
import com.example.tsalida.composables.MoreTopBar
import com.example.tsalida.composables.PageCard
import com.example.tsalida.data.SongList
import com.example.tsalida.destination.MoreDestination
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage


@Composable
fun EndListPage(navController: NavHostController){
    Scaffold(topBar = { MoreListTopBar(MoreDestination.ENDLIST.label, navController) }) { padding->
        LazyColumn(Modifier.padding(top = padding.calculateTopPadding())) {
            items(items = SongList.endPages, key = {it.pageNo}){
                PageCard(it.pageNo, it.title, navController, MoreDestination.ENDLIST.route)
            }
        }
    }
}


@Composable
fun EndPage(navController: NavHostController, startPage: Int = 0){
    val pagerState = rememberPagerState(pageCount = {4})
    LaunchedEffect(startPage) {
        pagerState.scrollToPage(startPage)
    }
    Scaffold(topBar = {MoreTopBar(MoreDestination.END.label, navController,
        MoreDestination.END.route, pagerState)}) { innerPadding->
        HorizontalPager(pagerState) {page->
            ZoomableAsyncImage(String.format("file:///android_asset/End Pages/end%d.jpg", page+1), "End Page Image", Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()))
        }
    }
}