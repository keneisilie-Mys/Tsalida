package com.example.tsalida.repository

import android.content.Context
import com.example.tsalida.data.DatabaseHelper

class ThemeRepository(context: Context) {
    private val helper = DatabaseHelper(context)

    suspend fun getTheme(): Int{
        val themeValue = helper.getTheme(helper.readableDatabase)
        return themeValue
    }
}