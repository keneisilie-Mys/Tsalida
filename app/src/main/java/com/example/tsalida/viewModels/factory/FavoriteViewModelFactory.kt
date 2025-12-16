package com.example.tsalida.viewModels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsalida.repository.FavoriteRepository
import com.example.tsalida.viewModels.FavoriteViewModel

class FavoriteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(FavoriteRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}