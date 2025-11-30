package com.example.tsalida.data

import android.provider.BaseColumns


//This is the contract class
object TsalidaEntries : BaseColumns{
    const val TABLE_NAME = "favorite"
    const val COLUMN_NAME_FAV = "isFavorite"
}