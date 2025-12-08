package com.example.tsalida

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tsalida.data.DatabaseHelper
import com.example.tsalida.ui.theme.TsalidaTheme
import com.example.tsalida.viewModels.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.telephoto.zoomable.coil.ZoomableAsyncImage
import java.io.File
import java.io.FileOutputStream
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

enum class Destinations(
    val route: String,
    val label: String,
    val contentDescription: String,
    val iconId: Int
){
    LIST("list", "List" , "List Icon", R.drawable.list_svgrepo_com),
    SONGS("songs/{startPage}", "Songs", "Songs Icon", R.drawable.music_note_svgrepo_com),
    FAVORITES("favorites", "Favorites", "Favorites Icon", R.drawable.heart_svgrepo_com),
    MORE("more", "More", "More Icon", R.drawable.more_circle_smborder_svgrepo_com)
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
        actions = {IconButton(onClick = {databaseHelper.updateFavorite(databaseHelper.writableDatabase, pagerState.currentPage+1); isFav =  if(isFav == 0) 1 else 0}) {
            if (isFav == 0) Icon(painterResource(R.drawable.heart_empty_svgrepo_com), "Heart Icon", Modifier.size(25.dp)) else Icon(painterResource(R.drawable.heart_filled_svgrepo_com), "Heart Icon", Modifier.size(25.dp), tint = Color.Unspecified)
        }
        IconButton(onClick = {shareImage(context, pagerState.currentPage+1)}) {
            Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
        }})
}

fun shareImage(context: Context, pageNo: Int){
    try {
        val inputStream = context.assets.open("Hymns/p$pageNo.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val newFile = File(cachePath, "share_image.png")
        val stream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile)

        //Sharing two pages
        if (pageNo == 97 || pageNo == 167 || pageNo == 226 || pageNo == 242 || pageNo == 248 || pageNo == 254 || pageNo == 267 || pageNo == 274 || pageNo == 331 || pageNo == 427){
            specialIntent(context, pageNo+1, contentUri, cachePath)
            return
        }
        if (pageNo == 98 || pageNo == 168 || pageNo == 227 || pageNo == 243 || pageNo == 249 || pageNo == 255 || pageNo == 268 || pageNo == 275 || pageNo == 332 || pageNo == 428){
            specialIntent(context, pageNo-1, contentUri, cachePath)
            return
        }
        //Share only one page
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(contentUri, context.contentResolver.getType(contentUri))
            putExtra(Intent.EXTRA_STREAM, contentUri)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via..."))
    }catch (e: Exception){
        Log.d("ErrorTsalida", "ErrorTsalida")
    }
}

fun specialIntent(context: Context, pageNo: Int, contentUri: Uri, cachePath: File){
    try {
        val inputStream = context.assets.open("Hymns/p$pageNo.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        val newFile2 = File(cachePath, "share_image2.png")
        val stream = FileOutputStream(newFile2)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
        val contentUri2 = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile2)
        val arrayUri = ArrayList<Uri>()
        arrayUri.add(contentUri)
        arrayUri.add(contentUri2)
        val shareIntent2 = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayUri)
        }
        context.startActivity(Intent.createChooser(shareIntent2, "Share via..."))
    }catch (e: Exception){
        Log.d("Error in special Intent", "Error in special Intent")
    }
}



//Pages
@Composable
fun SongsPage(startPage: Int = 0){
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



fun fixSongNo(songNo: Int): Int{
    var pageNo = songNo
    if (songNo>97 && songNo<=166) pageNo += 1
    if (songNo>166 && songNo<=224) pageNo += 2
    if (songNo>224 && songNo<=239) pageNo += 3
    if (songNo>239 && songNo<=244) pageNo += 4
    if (songNo>244 && songNo<=249) pageNo += 5
    if (songNo>249 && songNo<=261) pageNo += 6
    if (songNo>261 && songNo<=267) pageNo += 7
    if (songNo>267 && songNo<=323) pageNo += 8
    if (songNo>323 && songNo<=418) pageNo += 9
    if (songNo>418 && songNo<=423) pageNo += 10

    return pageNo
}

@Composable
fun SongCard(songNo: Int = 1, angamiTitle: String = "Angami", englishTitle: String = "English", navController: NavHostController, onChangeDestination: (Int) -> Unit){
    val actualSongNo = fixSongNo(songNo)
    val context = LocalContext.current
    val helper = DatabaseHelper(LocalContext.current)
    var isFav by rememberSaveable { mutableIntStateOf(helper.getFavorite(helper.readableDatabase, actualSongNo)) }
    Surface(Modifier.fillMaxWidth().clickable{Log.d("Actual Song", "ActualSong : $actualSongNo, SongNo : $songNo");navController.navigate("songs/$actualSongNo"); onChangeDestination(
        Destinations.SONGS.ordinal)}, color = Color.White, shadowElevation = 1.dp) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {helper.updateFavorite(helper.writableDatabase, actualSongNo); isFav = if(isFav == 0) 1 else 0}) {
                    if(isFav == 0) Icon(painterResource(R.drawable.heart_empty_svgrepo_com),"Heart Icon", Modifier.size(25.dp)) else Icon(painterResource(R.drawable.heart_filled_svgrepo_com), "Heart Icon", Modifier.size(25.dp), tint = Color.Unspecified)
                }
                Spacer(Modifier.width(10.dp))
                IconButton(onClick = {shareImage(context, actualSongNo)}) {
                    Icon(painterResource(R.drawable.share_2_svgrepo_com), "Share Icon", Modifier.size(25.dp))
                }
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPage(navController: NavHostController, onChangeDestination: (Int) -> Unit, viewModel: ListViewModel = viewModel()){
    val filteredItems by viewModel.filteredList.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable {mutableStateOf("") }
    var isFocus by rememberSaveable { mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    Column {

        OutlinedTextField(placeholder = {Text("Search",
                            style = TextStyle(fontSize = 15.sp, color = Color.White))},
                            textStyle = TextStyle(fontSize = 15.sp),
                            shape = RoundedCornerShape(30.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp, 5.dp).onFocusChanged{focusState -> isFocus = focusState.isFocused},
                            value = searchQuery,
                            leadingIcon = {if (isFocus) Icon(painterResource(R.drawable.back_svgrepo_com),"Back Icon", tint = Color.White, modifier = Modifier.size(30.dp).padding(3.dp).clickable(onClick = {focusManager.clearFocus(); searchQuery = ""; viewModel.filterList("")})) else Icon(painterResource(R.drawable.search_icons8),"Search Icon", tint = Color.White, modifier = Modifier.size(30.dp).padding(3.dp))},
                            onValueChange = {searchQuery = it; viewModel.filterList(searchQuery)},
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(R.color.tsalida_list_color), unfocusedContainerColor = colorResource(R.color.tsalida_main_color), focusedContainerColor = colorResource(R.color.tsalida_main_color), unfocusedBorderColor = colorResource(R.color.tsalida_main_color), focusedTextColor = Color.White),
                            trailingIcon = {if (isFocus) Icon(painterResource(R.drawable.cross_svgrepo_com), "Cross Icon", Modifier.size(30.dp).clickable(onClick = {searchQuery = ""; viewModel.filterList("")}), tint = Color.White)}
                            )


        LazyColumn(contentPadding = PaddingValues(0.dp, 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            items(items = filteredItems, key = {it.songNo}){ item->
                SongCard(item.songNo, item.angamiTitle, item.englishTitle, navController, onChangeDestination)
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
fun AppNavHost(navHostController: NavHostController, startDestination: Destinations, modifier: Modifier, onChangeDestination: (Int) -> Unit){
    NavHost(navHostController, startDestination.route, modifier = modifier) {

        composable(Destinations.SONGS.route){ backStackEntry->
            var startPage: Int

            val argInRoute = backStackEntry.arguments?.getString("startPage")?: "{startPage}"
            startPage = if(argInRoute == "{startPage}") 1 else argInRoute.toInt()

            SongsPage(startPage-1)
        }
        composable(Destinations.LIST.route) {
            ListPage(navHostController, onChangeDestination)
        }
        composable(Destinations.FAVORITES.route) {
            FavoritesPage()
        }
        composable(Destinations.MORE.route) {
            MorePage()
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
        AppNavHost(navController, startDestination, Modifier.padding(bottom = contentPadding.calculateBottomPadding()).statusBarsPadding(), {num-> selectedDestinations = num})
    }
}