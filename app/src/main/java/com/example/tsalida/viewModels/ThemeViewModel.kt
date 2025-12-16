package com.example.tsalida.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsalida.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(repository: ThemeRepository): ViewModel() {
    private val _themeValue = MutableStateFlow(1)
    val themeValue: StateFlow<Int> = _themeValue
    init {
        viewModelScope.launch {
            _themeValue.value = repository.getTheme()
        }
    }

    fun changeThemeValue(tValue: Int){
        _themeValue.value = tValue
    }
}