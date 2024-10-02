package com.example.tsalida.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tsalida.R;
import com.google.android.material.appbar.AppBarLayout;

public class MoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("More");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        AppBarLayout appBarLayout = activity.findViewById(R.id.appBarLayout);
        if(appBarLayout != null){
            appBarLayout.setExpanded(true, true);
        }

        View view = inflater.inflate(R.layout.fragment_more, container, false);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);

        button1.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResponsiveFragment(), "ResponsiveList").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN).commit();
        });

        button2.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EndFragment(), "EndList").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN).commit();
        });
        // Inflate the layout for this fragment
        return view;
    }
}