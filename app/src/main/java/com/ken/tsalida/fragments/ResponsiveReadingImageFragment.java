package com.ken.tsalida.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ken.tsalida.DepthPageTransformer;
import com.ken.tsalida.adapters.MoreImagesAdapter;
import com.ken.tsalida.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResponsiveReadingImageFragment extends Fragment {
    private int position = 0;

    public ResponsiveReadingImageFragment(){}

    public ResponsiveReadingImageFragment(int position){
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_responsive_reading_image, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Getting back the collapsed toolbar
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.appBarLayout);
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true, true);
        }

        //Getting Back the bottom nav if it is scrolled down
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        bottomNavigationView.animate().translationY(0).setDuration(300);

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResponsiveFragment()).commit();
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        int[] imageId = new int[32];

        for(int i = 0; i<32; i++){
            String name = "prelude"+(i+1);
            imageId[i] = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
        }

        ViewPager2 viewPager2 = view.findViewById(R.id.view_pagerRR);
        MoreImagesAdapter adapter = new MoreImagesAdapter(imageId);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(position, false);

        return view;
    }
}