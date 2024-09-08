package com.example.tsalida;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ListSongFragment extends Fragment {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);

        ////Gonna pass song titles to the song objects
        List<Song> songList = new ArrayList<>(); //This is the song object array
        AssetManager assetManager = getContext().getAssets();
        AssetManager assetManager2 = getContext().getAssets();
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

        }catch (Exception e){
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //Passing the datas to the adapter
        SongTitleAdapter adapter = new SongTitleAdapter(songList, (SongTitleAdapter.Listener) getActivity());

        //Get the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        //The layout for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;


    }
}