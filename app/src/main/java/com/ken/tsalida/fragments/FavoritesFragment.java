package com.ken.tsalida.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ken.tsalida.R;
import com.ken.tsalida.adapters.SongTitleAdapterFav;
import com.google.android.material.appbar.AppBarLayout;

public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Favorites");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        AppBarLayout appBarLayout = activity.findViewById(R.id.appBarLayout);
        if(appBarLayout != null){
            appBarLayout.setExpanded(true, true);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewFAv);
        SongTitleAdapterFav adapter = new SongTitleAdapterFav(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}