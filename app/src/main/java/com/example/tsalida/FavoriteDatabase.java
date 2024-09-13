package com.example.tsalida;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDatabase extends SQLiteOpenHelper {
    FavoriteDatabase(Context context){
        super(context, "FavoritesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateDatabase(sqLiteDatabase, 0,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateDatabase(sqLiteDatabase, 1,2);
    }

    public void updateDatabase(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        if(oldVersion<1){
            sqLiteDatabase.execSQL("CREATE TABLE FAVORITES(_id INTEGER PRIMARY KEY AUTOINCREMENT, IS_FAVORITE INTEGER DEFAULT 0 NOT NULL)");
            int num = 0;
            while(num<423){
                insertInDatabase(sqLiteDatabase);
                num++;
            }
        }
    }

    private void insertInDatabase(SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IS_FAVORITE", 0);
        sqLiteDatabase.insert("FAVORITES", null, contentValues);
    }
}
