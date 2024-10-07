package com.ken.tsalida.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ken.tsalida.R;
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
        CardView button1 = view.findViewById(R.id.card_view1);
        CardView button2 = view.findViewById(R.id.card_view2);
        CardView button3 = view.findViewById(R.id.card_view3);

        button1.setOnClickListener(view1 -> {
            view1.postDelayed(()->{
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out).replace(R.id.fragmentContainer, new ResponsiveFragment(), "ResponsiveList").commit();
            }, 100);
        });

        button2.setOnClickListener(view1 -> {
            view1.postDelayed(()->{
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out).replace(R.id.fragmentContainer, new EndFragment(), "EndList").commit();
            }, 100);
        });
        // Inflate the layout for this fragment
        return view;
    }
}