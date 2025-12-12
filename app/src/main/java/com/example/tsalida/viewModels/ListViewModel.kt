package com.example.tsalida.viewModels

import androidx.lifecycle.ViewModel
import com.example.tsalida.data.Song
import com.example.tsalida.data.SongList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ListViewModel: ViewModel(){
    private val songs = SongList.songs
    private val _filteredList = MutableStateFlow(songs)
    var filteredList: StateFlow<List<Song>> = _filteredList

    fun filterList(queryString: String){
        _filteredList.value = songs.filter { it.songNo.toString().contains(queryString, ignoreCase = true) || it.angamiTitle.contains(queryString, ignoreCase = true) || it.englishTitle.contains(queryString, ignoreCase = true)}
    }
}