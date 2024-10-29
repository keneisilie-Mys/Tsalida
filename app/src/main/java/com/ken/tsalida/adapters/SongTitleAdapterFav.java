package com.ken.tsalida.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ken.tsalida.FavoriteDatabase;
import com.ken.tsalida.R;
import com.ken.tsalida.Song;
import com.ken.tsalida.fragments.FavoritesFragment2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SongTitleAdapterFav extends RecyclerView.Adapter<SongTitleAdapterFav.ViewHolder> {
    List<Song> songList;
    List<Song> favList;
    Context context;

    //The adapter constructor
    public SongTitleAdapterFav(Context context){
        this.context = context;
        this.songList = getSongLists();
        this.favList = updateList(songList);
    }
    private List<Song> getSongLists(){
        List<Song> songList = new ArrayList<>(); //This is the song object array
        AssetManager assetManager = context.getAssets();
        AssetManager assetManager2 = context.getAssets();
        try{
            InputStream inputStream = assetManager.open("angamiTitle.txt");
            InputStream inputStream2 = assetManager2.open("englishTitle.txt");

            BufferedReader localReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader englishReader = new BufferedReader(new InputStreamReader(inputStream2));

            String localName, englishName;
            int index = 1;
            while((localName = localReader.readLine())!=null && (englishName = englishReader.readLine())!= null){
                Song song = new Song(localName, englishName, index);
                songList.add(song);
                index++;
            }
            Log.d("Enter getsongLists", "End of the list");
        }catch (Exception e){
            e.printStackTrace();
        }

        return songList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }

    @Override
    public int getItemCount() {
        return favList.toArray().length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.song_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView english = cardView.findViewById(R.id.english_title);
        TextView angami = cardView.findViewById(R.id.angami_title);
        TextView number = cardView.findViewById(R.id.index);
        ImageButton favButton = cardView.findViewById(R.id.favorite_button);

        //Passing the position to a variable to kind of make it final
        Song song = favList.get(position);

        english.setText(song.getEnglishTitle());
        angami.setText(song.getLocalTitle());
        number.setText(String.valueOf(song.getSongIndex()));
        favButton.setImageResource(R.drawable.favorite_filled_icon);
        int finalPosition = position;

        //Setting the on click listener to the card view(View Holder)
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.postDelayed(()->{
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out).replace(R.id.fragmentContainer, new FavoritesFragment2(finalPosition), "FavoriteFragment2").commit();
                },100);
            }
        });

        //Setting the on Click Listener to the image button
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase(song.getSongIndex(), 0);
                favList = updateList(songList);
                favButton.animate().setDuration(100).scaleX(1.5f).scaleY(1.5f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        favButton.animate().setDuration(50).scaleX(1.0f).scaleY(1.0f);
                    }
                });
                cardView.animate().setDuration(200).translationX(-900.0f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cardView.animate().setDuration(1).translationX(1.0f);
                    }
                });
                view.postDelayed(()->{
                    filterList(favList);
                }, 200);
            }
        });

    }

    private void updateDatabase(int position, int tag) {
        try(SQLiteOpenHelper openHelper = new FavoriteDatabase(context);
            SQLiteDatabase database = openHelper.getReadableDatabase()){

            ContentValues contentValues = new ContentValues();
            contentValues.put("IS_FAVORITE", tag);
            database.update("FAVORITES", contentValues, "_id=?", new String[]{(Integer.toString(position))});



        }catch(Exception e){
            Log.d("Enter2Error", "Enter2error");
        }
    }


    public void filterList(List<Song> filteredList){
        favList = filteredList;
        notifyDataSetChanged();
    }


    //Method copied from favorites fragment to be used with it
    public List<Song> updateList(List<Song> songList) {
        List<Song> newList = new ArrayList<>();
        try(SQLiteOpenHelper sqLiteOpenHelper = new FavoriteDatabase(context);
            SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase()){
            Cursor cursor = database.query("FAVORITES", new String[]{"_id, IS_FAVORITE"}, "IS_FAVORITE = ?", new String[]{"1"}, null, null, null);
            ////////////////////////////
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(0);
                    for(Song song: songList){
                        if(song.getSongIndex() == id){
                            newList.add(song);
                            break;
                        }
                    }
                }while(cursor.moveToNext());
            }
        }
        return newList;
    }
}
