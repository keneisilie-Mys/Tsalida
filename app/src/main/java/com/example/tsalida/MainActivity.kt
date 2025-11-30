package com.example.tsalida

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tsalida.data.DatabaseHelper
import com.example.tsalida.data.SongList
import com.example.tsalida.ui.theme.TsalidaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import java.util.Locale


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var setSplashScreen = true
        splashScreen.setKeepOnScreenCondition { setSplashScreen }
        lifecycleScope.launch {
            delay(2000)
            setSplashScreen = false
        }
        enableEdgeToEdge()
        setContent {
            TsalidaTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(colorResource(R.color.tsalida_main_color))) { innerPadding ->
                    TsalidaApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

const val TAG = "TsalidaLog"

enum class Destinations(
    val route: String,
    val label: String,
    val contentDescription: String,
    val iconId: Int
){
    LIST("list", "List" , "List Icon", R.drawable.list_svgrepo_com),
    SONGS("songs", "Songs", "Songs Icon", R.drawable.music_note_svgrepo_com),
    FAVORITES("favorites", "Favorites", "Favorites Icon", R.drawable.heart_svgrepo_com),
    MORE("more", "More", "More Icon", R.drawable.more_circle_smborder_svgrepo_com)
}



fun updateDatabase(pagerState: PagerState, context: Context){
    val databaseHelper = DatabaseHelper(context)
    val db = databaseHelper.writableDatabase
    databaseHelper.updateFavorite(db, pagerState.currentPage + 1)
}

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
        actions = {IconButton(onClick = {updateDatabase(pagerState, context); if(isFav == 0) isFav = 1 else isFav = 0}) {
            if (isFav == 0) Icon(painterResource(R.drawable.heart_empty_svgrepo_com), "Heart Icon", Modifier.size(25.dp)) else Icon(painterResource(R.drawable.heart_filled_svgrepo_com), "Heart Icon", Modifier.size(25.dp), tint = Color.Unspecified)
        }
        IconButton(onClick = {}) {
            Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
        }})
}

//Pages
@Composable
fun SongsPage(){
    val pageState = rememberPagerState(pageCount = {432})
    Scaffold(topBar = {SongsBar(pageState)}, containerColor = Color.White) { innerPadding->
        HorizontalPager(state = pageState, modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())) { page->
            ZoomableAsyncImage(String.format(Locale.US, "file:///android_asset/Hymns/p%d.jpg", page+1), "Image")
        }
    }
}


@Preview
@Composable
fun SongCard(songNo: Int = 1, angamiTitle: String = "Angami", englishTitle: String = "English", navController: NavHostController){
    Surface(Modifier.fillMaxWidth().clickable{navController.navigate(Destinations.SONGS.route)}, color = Color.White, shadowElevation = 1.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(10.dp))
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$songNo", modifier = Modifier.width(50.dp), textAlign = TextAlign.Center, color = colorResource(R.color.tsalida_list_color), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Column(Modifier.padding(5.dp)) {
                    Text(angamiTitle, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = colorResource(R.color.tsalida_list_color))
                    Spacer(Modifier.height(3.dp))
                    Text(englishTitle, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                }
            }
            Row() {
                Icon(painterResource(R.drawable.heart_svgrepo_com), "Heart Icon", Modifier.size(25.dp))
                Spacer(Modifier.width(10.dp))
                Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}

@Composable
fun ListPage(navController: NavHostController){
    Column() {
        LazyColumn(contentPadding = PaddingValues(0.dp, 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(SongList.songs){song ->
                SongCard(song.songNo, song.angamiTitle, song.englishTitle, navController)
            }
        }
    }
}
@Composable
fun FavoritesPage(){
    Text("Favorites Page")
}
@Composable
fun MorePage(){
    Text("More Page")
}


@Composable
fun AppNavHost(navHostController: NavHostController, startDestination: Destinations, modifier: Modifier){
    NavHost(navHostController, startDestination.route, modifier = modifier) {
        Destinations.entries.forEach { destinations ->
            composable(destinations.route){
                when(destinations){
                    Destinations.SONGS -> SongsPage()
                    Destinations.LIST -> ListPage(navHostController)
                    Destinations.FAVORITES -> FavoritesPage()
                    Destinations.MORE -> MorePage()
                }
            }
        }
    }
}


@Composable
fun myCustomNavColors(): NavigationBarItemColors {    return NavigationBarItemDefaults.colors(
    // Define your specific override colors here
    selectedIconColor = colorResource(R.color.black_text), // Your Tsalida main color?
    selectedTextColor = colorResource(R.color.black_text),

    unselectedIconColor = colorResource(R.color.black_text_lighter),
    unselectedTextColor = colorResource(R.color.black_text_lighter),

    indicatorColor = colorResource(R.color.tsalida_main_color)
)
}

@Composable
fun BottomNavBar(navController: NavController,  selectedDestinations: Int, onChangeDestination: (Int)-> Unit){
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, modifier = Modifier.clip(
        RoundedCornerShape(10.dp, 10.dp)), containerColor = Color.Transparent, tonalElevation = 500.dp) {
        Destinations.entries.forEachIndexed { index, destinations ->
            NavigationBarItem(selected = selectedDestinations == index, onClick = {navController.navigate(destinations.route) ; onChangeDestination(index)}, icon = {Icon(
                ImageVector.vectorResource(destinations.iconId), destinations.contentDescription, modifier = Modifier.size(20.dp))}, label = {Text(destinations.label)}, colors = myCustomNavColors())
        }
    }
}

@Composable
fun TsalidaApp(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val startDestination = Destinations.SONGS
    var selectedDestinations by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    Scaffold(modifier = Modifier, containerColor = Color.White, bottomBar = {BottomNavBar(navController, selectedDestinations) { num ->
        selectedDestinations = num
    }
    }) {
        contentPadding ->
        AppNavHost(navController, startDestination, Modifier.padding(bottom = contentPadding.calculateBottomPadding()).statusBarsPadding())
    }
}