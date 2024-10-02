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

public class EndImageFragment extends Fragment {

    private int position = 0;

    public EndImageFragment(){}

    public EndImageFragment(int position){
        if(position == 3){
            position = position-1;
        }
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

        //Bringing back appbar and bottomnav if they are collapsed
        AppBarLayout appBarLayout = activity.findViewById(R.id.appBarLayout);
        if(appBarLayout!=null){
            appBarLayout.setExpanded(true, true);
        }
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
        bottomNavigationView.animate().translationY(0).setDuration(300);

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EndFragment(), "EndFrag").commit();
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        int[] imageId = new int[3];

        for(int i = 0; i<3; i++){
            String name = "end"+(i+1);
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