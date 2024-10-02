package com.ken.tsalida;

public class Song {
    String englishTitle;
    String localTitle;
    int SongIndex;

    public Song(String localTitle, String englishTitle, int SongIndex){
        this.localTitle = localTitle;
        this.englishTitle = englishTitle;
        this.SongIndex = SongIndex;
    }
    public String getEnglishTitle(){
        return this.englishTitle;
    }
    public String getLocalTitle(){
        return this.localTitle;
    }
    public int getSongIndex(){
        return this.SongIndex;
    }

}
