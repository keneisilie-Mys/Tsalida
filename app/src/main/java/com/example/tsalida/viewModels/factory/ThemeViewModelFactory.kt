package com.example.tsalida.viewModels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsalida.repository.ThemeRepository
import com.example.tsalida.viewModels.ThemeViewModel

class ThemeViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)){
            return ThemeViewModel(ThemeRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}