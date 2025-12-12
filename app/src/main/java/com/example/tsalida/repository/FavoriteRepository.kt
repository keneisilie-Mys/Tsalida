package com.example.tsalida.repository

import android.content.Context
import com.example.tsalida.data.DatabaseHelper
import com.example.tsalida.data.Song
import com.example.tsalida.data.SongList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoriteRepository(context: Context) {
    private val helper = DatabaseHelper(context)

    fun getFavoriteSongs(): Flow<List<Song>> = flow {
        val favoriteIds = helper.getFavoriteList(helper.readableDatabase)
        val favArrayList = SongList.songs.filter { it.songNo in favoriteIds }
        emit(favArrayList)
    }.flowOn(Dispatchers.IO)
}