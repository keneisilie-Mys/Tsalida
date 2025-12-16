package com.example.tsalida.destination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tsalida.R
import com.example.tsalida.composables.SongCard
import com.example.tsalida.viewModels.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPage(navController: NavHostController, onChangeDestination: (Int) -> Unit, viewModel: ListViewModel = viewModel()){
    onChangeDestination(Destinations.LIST.ordinal)
    val filteredItems by viewModel.filteredList.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable {mutableStateOf("") }
    var isFocus by rememberSaveable { mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {

        OutlinedTextField(placeholder = {Text("Search", style = MaterialTheme.typography.labelLarge)},
            textStyle = MaterialTheme.typography.labelLarge,
            shape = RoundedCornerShape(30.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(20.dp, 5.dp).onFocusChanged{focusState -> isFocus = focusState.isFocused},
            value = searchQuery,
            leadingIcon = { IconButton(modifier = Modifier.size(30.dp).padding(3.dp), onClick = {if (isFocus){searchQuery = ""; viewModel.filterList(""); isFocus = !isFocus; focusManager.clearFocus(force = true); keyboardController?.hide()} }){
                if (isFocus) Icon(painterResource(R.drawable.back_svgrepo_com),"Back Icon") else Icon(painterResource(R.drawable.search_25dp),"Search Icon")
            }},
            onValueChange = {searchQuery = it; viewModel.filterList(searchQuery)},
            //colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(R.color.tsalida_list_color), unfocusedContainerColor = colorResource(R.color.tsalida_main_color), focusedContainerColor = colorResource(R.color.tsalida_main_color), unfocusedBorderColor = colorResource(R.color.tsalida_main_color), focusedTextColor = Color.White),
            trailingIcon = {if (isFocus) Icon(painterResource(R.drawable.cross_svgrepo_com), "Cross Icon", Modifier.size(30.dp).clip(CircleShape).clickable(onClick = {searchQuery = ""; viewModel.filterList("")}))}
        )


        LazyColumn(contentPadding = PaddingValues(0.dp, 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            items(items = filteredItems, key = {it.songNo}){ item->
                SongCard(item.songNo, item.angamiTitle, item.englishTitle, navController)
            }
        }
    }
}