package com.example.tsalida;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongTitleAdapter extends RecyclerView.Adapter<SongTitleAdapter.ViewHolder> {
    List<Song> songLists2;
    //Making an interface
    interface Listener{
        void onClickViewHolder(int songIndex);
    }
    private final Listener listener;

    //The adapter constructor
    SongTitleAdapter(List<Song> songLists2, Listener listener){
        this.songLists2 = songLists2;
        this.listener = listener;
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
        return songLists2.toArray().length;
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

        Song song = songLists2.get(position);
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
    }
}
