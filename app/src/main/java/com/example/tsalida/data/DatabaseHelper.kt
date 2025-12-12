package com.example.tsalida.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

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

    fun getFavoriteList(db: SQLiteDatabase):ArrayList<Int>{
        val cursor = db.query(TsalidaEntries.TABLE_NAME, arrayOf(BaseColumns._ID), "${TsalidaEntries.COLUMN_NAME_FAV} = ?", arrayOf("1"), null, null, null)
        val favoriteList = ArrayList<Int>()
        while (cursor.moveToNext()){
            favoriteList.add(cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)))
        }
        cursor.close()
        return favoriteList
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
        specialFavUpdate(db, pageNo, row)
    }

    fun specialFavUpdate(db: SQLiteDatabase, pageNo: Int, row: ContentValues){
        //97 167 226 242 248 254 267 274 331 428
        if (pageNo == 97 || pageNo == 167 || pageNo == 226 || pageNo == 242 || pageNo == 248 || pageNo == 254 || pageNo == 267 || pageNo == 274 || pageNo == 331 || pageNo == 427){
            val pageNo2 = pageNo+1
            db.update(TsalidaEntries.TABLE_NAME, row, "${BaseColumns._ID} = ?", arrayOf("$pageNo2"))
        }
        if (pageNo == 98 || pageNo == 168 || pageNo == 227 || pageNo == 243 || pageNo == 249 || pageNo == 255 || pageNo == 268 || pageNo == 275 || pageNo == 332 || pageNo == 428){
            val pageNo2 = pageNo-1
            db.update(TsalidaEntries.TABLE_NAME, row, "${BaseColumns._ID} = ?", arrayOf("$pageNo2"))
        }
    }

    companion object{
        const val DATABASE_NAME = "Tsalida.db"
        const val DATABASE_VERSION = 1
        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${TsalidaEntries.TABLE_NAME} (" + "${BaseColumns._ID} INTEGER PRIMARY KEY, " + "${TsalidaEntries.COLUMN_NAME_FAV} INTEGER)"
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TsalidaEntries.TABLE_NAME}"
    }
}