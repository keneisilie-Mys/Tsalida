package com.example.tsalida;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SongLists {
    private String[] angamiTitles;
    private String[] englishTitles;
    private Context context;

    SongLists(Context context){
        this.context = context;
    }

    public String[] getAngamiTitle(){
        angamiTitles = fetchItem("angamiTitle.txt");
        return this.angamiTitles;
    }
    public String[] getEnglishTitles(){
        englishTitles = fetchItem("englishTitle.txt");
        return this.englishTitles;
    }

    private String[] fetchItem(String fileName){
        AssetManager assetManager = context.getAssets();
        List<String> titleList = new ArrayList<>();

        try{
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String title;
            while((title = reader.readLine()) != null){
                titleList.add(title);
            }
            inputStream.close();
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return titleList.toArray(new String[0]);
    }
}
