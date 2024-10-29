package com.ken.tsalida.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.ken.tsalida.R;
import com.ken.tsalida.Song;
import com.ken.tsalida.adapters.SongTitleAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ListSongFragment extends Fragment {

    private boolean isAlpha = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);

        //Change the toolbar name
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("List");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ////Gonna read song titles from ASSETS and add it to the song objects
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
        List<Song> songListSort = new ArrayList<>(songList);
        songListSort.sort((one, two) -> {return one.getLocalTitle().compareTo(two.getLocalTitle());});
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Passing the data to the adapter
        SongTitleAdapter adapter = new SongTitleAdapter(songList, getActivity());


        //Adding menu to the toolbar
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.list_song_menu, menu);
                MenuItem item = menu.findItem(R.id.item1);
                MenuItem item2 = menu.findItem(R.id.item2);
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Search");
                searchView.setMaxWidth(Integer.MAX_VALUE);

                //Listening for the expanded search view
                //Remember that you had to set item2 showasaction as 'always', else it moves to the overflow area when the search view is clicked
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(@NonNull MenuItem menuItem) {
                        item2.setVisible(false);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(@NonNull MenuItem menuItem) {
                        item2.setVisible(true);
                        return true;
                    }
                });


                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        List<Song> filteredList = new ArrayList<>();

                        if(isAlpha){
                            for(Song song: songList){
                                if((Integer.toString(song.getSongIndex()).contains(newText)) || song.getEnglishTitle().toLowerCase().contains(newText.toLowerCase()) || song.getLocalTitle().toLowerCase().contains(newText.toLowerCase())){
                                    filteredList.add(song);
                                }
                            }
                        }
                        else {
                            for(Song song: songListSort){
                                if((Integer.toString(song.getSongIndex()).contains(newText)) || song.getEnglishTitle().toLowerCase().contains(newText.toLowerCase()) || song.getLocalTitle().toLowerCase().contains(newText.toLowerCase())){
                                    filteredList.add(song);
                                }
                            }
                        }
                        adapter.filterList(filteredList);
                        return false;
                    }
                });

            }


            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.item2){
                    if(isAlpha){
                        menuItem.setIcon(R.drawable.num_sort_icon);
                        adapter.filterList(songListSort);
                        isAlpha = false;
                    }
                    else{
                        adapter.filterList(songList);
                        menuItem.setIcon(R.drawable.alpha_sort_icon);
                        isAlpha = true;
                    }

                }
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