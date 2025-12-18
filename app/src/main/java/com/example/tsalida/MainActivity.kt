package com.example.tsalida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tsalida.data.DatabaseHelper
import com.example.tsalida.destination.Destinations
import com.example.tsalida.destination.FavoritesPage
import com.example.tsalida.destination.ListPage
import com.example.tsalida.destination.MorePage
import com.example.tsalida.destination.SongsPage
import com.example.tsalida.ui.theme.TsalidaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.tsalida.destination.MoreDestination
import com.example.tsalida.destination.more.EndListPage
import com.example.tsalida.destination.more.EndPage
import com.example.tsalida.destination.more.ReadingListPage
import com.example.tsalida.destination.more.ReadingPage
import com.example.tsalida.destination.more.ThemePage
import com.example.tsalida.viewModels.ThemeViewModel
import com.example.tsalida.viewModels.factory.ThemeViewModelFactory


class MainActivity : ComponentActivity() {
    val themeViewModel: ThemeViewModel by viewModels { ThemeViewModelFactory(applicationContext) }
    override fun onPause() {
        super.onPause()
        val helper = DatabaseHelper(applicationContext)
        helper.updateTheme(helper.writableDatabase, themeViewModel.themeValue.value)
    }

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
            val themeValue by themeViewModel.themeValue.collectAsStateWithLifecycle()
            TsalidaTheme(themeValue) {
                TsalidaApp(themeValue, {value-> themeViewModel.changeThemeValue(value)})
            }
        }
    }
}



@Composable
fun AppNavHost(navHostController: NavHostController, startDestination: Destinations, modifier: Modifier, onChangeDestination: (Int) -> Unit, changeThemeValue: (Int) -> Unit, themeValue: Int){
    NavHost(navHostController, startDestination.route, modifier = modifier) {

        composable(Destinations.SONGS.route){ backStackEntry->
            val argInRoute = backStackEntry.arguments?.getString("startPage")?: "{startPage}"
            val startPage = if(argInRoute == "{startPage}") 1 else argInRoute.toInt()
            SongsPage(startPage-1, onChangeDestination)
        }
        composable(Destinations.LIST.route) {
            ListPage(navHostController, onChangeDestination)
        }
        composable(Destinations.FAVORITES.route) {
            FavoritesPage(navHostController, onChangeDestination)
        }
        composable(Destinations.MORE.route) {
            MorePage(navHostController, onChangeDestination)
        }


        composable(MoreDestination.READINGLIST.route) {
            ReadingListPage(navHostController)
        }
        composable(MoreDestination.READING.route){backStackEntry->
            val argInRoute = backStackEntry.arguments?.getString("pageNo")?: "{pageNo}"
            val pageNo = if(argInRoute == "{pageNo}") 1 else argInRoute.toInt()
            ReadingPage(navHostController, pageNo-1)
        }
        composable(MoreDestination.ENDLIST.route){
            EndListPage(navHostController)
        }
        composable(MoreDestination.END.route){backStackEntry->
            val argInRoute = backStackEntry.arguments?.getString("pageNo")?: "{pageNo}"
            val pageNo = if(argInRoute == "{pageNo}") 1 else argInRoute.toInt()
            EndPage(navHostController, pageNo-1)
        }
        composable(MoreDestination.THEME.route) {
            ThemePage(navHostController, themeValue, changeThemeValue)
        }
    }
}


//@Composable
//fun myCustomNavColors(): NavigationBarItemColors {    return NavigationBarItemDefaults.colors(
//    // Define your specific override colors here
//    selectedIconColor = colorResource(R.color.black_text), // Your Tsalida main color?
//    selectedTextColor = colorResource(R.color.black_text),
//
//    unselectedIconColor = colorResource(R.color.black_text_lighter),
//    unselectedTextColor = colorResource(R.color.black_text_lighter),
//
//    indicatorColor = colorResource(R.color.tsalida_main_color)
//)
//}

@Composable
fun BottomNavBar(navController: NavController,  selectedDestinations: Int, onChangeDestination: (Int)-> Unit){
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, modifier = Modifier.clip(
        RoundedCornerShape(10.dp, 10.dp))) {
        Destinations.entries.forEachIndexed { index, destinations ->
            NavigationBarItem(selected = selectedDestinations == index, onClick = {navController.popBackStack(
                Destinations.SONGS.route, index == 1);navController.navigate(destinations.route) ; onChangeDestination(index)}, icon = {Icon(
                ImageVector.vectorResource(destinations.iconId), destinations.contentDescription, modifier = Modifier.size(20.dp))}, label = {Text(destinations.label)})
        }
    }
}

@Composable
fun TsalidaApp(themeValue: Int, changeThemeValue: (Int)->Unit){
    val navController = rememberNavController()
    val startDestination = Destinations.SONGS
    var selectedDestinations by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    Scaffold(bottomBar = {BottomNavBar(navController, selectedDestinations) { num ->
        selectedDestinations = num
    }
    }) {
        contentPadding ->
        Column() {
            AppNavHost(navController, startDestination, Modifier.padding(bottom = contentPadding.calculateBottomPadding()).statusBarsPadding(), {num-> selectedDestinations = num}, changeThemeValue, themeValue)
        }
    }
}