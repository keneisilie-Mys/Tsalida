package com.example.tsalida.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)

        var i=1
        while(i<=433){
            val row = ContentValues().apply {
                put(TsalidaEntries.COLUMN_NAME_FAV, 0)
            }
            db.insert(TsalidaEntries.TABLE_NAME, null, row)
            i++
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun getFavorite(db: SQLiteDatabase, pageNo: Int): Int {
        val projection = arrayOf(TsalidaEntries.COLUMN_NAME_FAV)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$pageNo")
        val cursor = db.query(TsalidaEntries.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        var isFavorite = 0
        while (cursor.moveToNext()){
            isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(TsalidaEntries.COLUMN_NAME_FAV))
        }
        cursor.close()

        return isFavorite
    }

    fun updateFavorite(db: SQLiteDatabase, pageNo: Int){
        val projection = arrayOf(TsalidaEntries.COLUMN_NAME_FAV)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$pageNo")
        val cursor = db.query(TsalidaEntries.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        var isFavorite = 0
        while (cursor.moveToNext()){
            isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(TsalidaEntries.COLUMN_NAME_FAV))
        }
        cursor.close()

        val row = ContentValues().apply {
            put(TsalidaEntries.COLUMN_NAME_FAV, if(isFavorite == 0)1 else 0)
        }
        db.update(TsalidaEntries.TABLE_NAME, row, "${BaseColumns._ID} = ?", arrayOf("$pageNo"))
    }

    companion object{
        const val DATABASE_NAME = "Tsalida.db"
        const val DATABASE_VERSION = 1
        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${TsalidaEntries.TABLE_NAME} (" + "${BaseColumns._ID} INTEGER PRIMARY KEY, " + "${TsalidaEntries.COLUMN_NAME_FAV} INTEGER)"
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TsalidaEntries.TABLE_NAME}"
    }
}