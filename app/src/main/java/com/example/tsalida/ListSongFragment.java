package com.example.tsalida;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ListSongFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);

        //Get the toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("List of Hymns");


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

        //Adding menu to the toolbar
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.list_song_menu, menu);
                MenuItem item = menu.findItem(R.id.item1);
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Search");

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        List<Song> filteredList = new ArrayList<>();

                        for(Song song: songList){
                            if((Integer.toString(song.getSongIndex()).contains(newText)) || song.getEnglishTitle().toLowerCase().contains(newText.toLowerCase()) || song.getLocalTitle().toLowerCase().contains(newText.toLowerCase())){
                                filteredList.add(song);
                            }
                        }
                        if(newText == null){
                            //Setting the default value to the adapter
                            adapter.filterList(songList);
                        }else{
                            adapter.filterList(filteredList);
                        }
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);



        //Get the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        //The layout for the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;


    }
}