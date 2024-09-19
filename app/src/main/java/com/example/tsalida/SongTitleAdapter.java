package com.example.tsalida;

import android.content.ContentValues;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongTitleAdapter extends RecyclerView.Adapter<SongTitleAdapter.ViewHolder> {
    List<Song> songLists;
    Context context;
    //Making an interface
    interface Listener{
        void onClickViewHolder(int songIndex);
    }
    private final Listener listener;

    //The adapter constructor
    SongTitleAdapter(List<Song> songLists, Context context){
        this.songLists = songLists;
        this.context = context;
        this.listener = (Listener) context;
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
        return songLists.toArray().length;
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

        Song song = songLists.get(position);

        //Setting the favorite icon based on the data in the database
        try{
            SQLiteOpenHelper database = new FavoriteDatabase(context);
            SQLiteDatabase db = database.getReadableDatabase();

            Cursor cursor = db.query("FAVORITES", new String[]{"_id", "IS_FAVORITE"}, "_id=?", new String[]{Integer.toString(song.getSongIndex())}, null, null, null);

            boolean isFavorite = false;
            if(cursor.moveToFirst()){
                isFavorite = (cursor.getInt(1) == 1);
            }

            if(isFavorite){
                favButton.setTag("1");
                favButton.setImageResource(R.drawable.favorite_filled_icon);
            }else{
                favButton.setImageResource(R.drawable.favorites_icon);
            }
            db.close();
            cursor.close();

        }catch(Exception e){
            Log.d("Enter1Error", "Enter1error");
        }

        english.setText(song.getEnglishTitle());
        angami.setText(song.getLocalTitle());
        number.setText(String.valueOf(song.getSongIndex()));

        //Setting the on click listener to the card view(View Holder)
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClickViewHolder(song.getSongIndex() - 1);
                }
            }
        });
        //Setting the on Click Listener to the image button
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((favButton.getTag()).equals("0")){  //use equals because the getTag returns a String object
                    favButton.setImageResource(R.drawable.favorite_filled_icon);
                    updateDatabase(song.getSongIndex(), 1);
                    favButton.setTag("1");
                }else{
                    favButton.setImageResource(R.drawable.favorites_icon);
                    updateDatabase(song.getSongIndex(), 0);
                    favButton.setTag("0");
                }
            }
        });

    }

    private void updateDatabase(int position, int tag) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("IS_FAVORITE", tag);


            SQLiteOpenHelper openHelper = new FavoriteDatabase(context);
            SQLiteDatabase database = openHelper.getReadableDatabase();
            database.update("FAVORITES", contentValues, "_id=?", new String[]{(Integer.toString(position))});

            database.close();
        }catch(Exception e){
            Log.d("Enter2Error", "Enter2error");
        }
    }


    public void filterList(List<Song> filteredList){
        songLists = filteredList;
        notifyDataSetChanged();
    }
}
