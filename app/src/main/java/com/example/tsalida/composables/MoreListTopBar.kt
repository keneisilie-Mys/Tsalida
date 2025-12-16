package com.example.tsalida.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tsalida.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreListTopBar(title: String, navController: NavHostController){
    TopAppBar(title = {Text(title, style = MaterialTheme.typography.titleLarge)},
        navigationIcon = { IconButton(onClick = {navController.navigateUp()}){
            Icon(painterResource(R.drawable.back_svgrepo_com), "Back Button", modifier = Modifier.size(25.dp))} })
}