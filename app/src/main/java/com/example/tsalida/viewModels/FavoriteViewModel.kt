package com.example.tsalida.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsalida.data.Song
import com.example.tsalida.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(val repository: FavoriteRepository): ViewModel() {
    val favList: StateFlow<List<Song>> = repository.getFavoriteSongs().stateIn(scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())
}
