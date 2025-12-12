package com.example.tsalida.destination

import com.example.tsalida.R

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
