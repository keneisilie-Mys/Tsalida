package com.example.tsalida;

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
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SongFragment extends Fragment {
    private int position;

    SongFragment() {
    }

    SongFragment(int position) {
        this.position = position;
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_song, container, false);

        //Getting back the collapsed toolbar
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.appBarLayout);
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true, true);
        }
        //Bringing back the bottom nav view if it is scrolled down through the list fragment
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        bottomNavigationView.animate().translationY(0).setDuration(300);

        //Changing the toolbar title in the main activity
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Hymn");
        //Changing the toolbar menu of the main activity
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.song_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        ////////////////////////////////////////
        ViewPager2 viewPager2 = rootView.findViewById(R.id.imageViewPager);

        //Getting the ids of the images with their names to an array
        int[] imageIds = new int[396];
        for (int i = 1; i <= 396; i++) {
            String itemName = "page" + i;
            int resId = getResources().getIdentifier(itemName, "drawable", getContext().getPackageName());
            imageIds[i - 1] = resId;
        }
        //Setting the image ids to the adapter then to the viewpager
        ImageAdapterr adapter = new ImageAdapterr(imageIds);
        viewPager2.setAdapter(adapter);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.setCurrentItem(position, false);

        // Inflate the layout for this fragment
        return rootView;
    }

}
