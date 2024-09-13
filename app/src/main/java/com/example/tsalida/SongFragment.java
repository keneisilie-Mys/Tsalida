package com.example.tsalida;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
    private Menu menuSong; //A menu reference because i want to access its icons
    private MenuItem menuItemSong;

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
        ViewPager2 viewPager2 = rootView.findViewById(R.id.imageViewPager);
        //Changing the toolbar menu of the main activity
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                menuInflater.inflate(R.menu.song_toolbar_menu, menu);
                MenuItem menuItem = menu.findItem(R.id.item1);
                menuItemSong = menu.findItem(R.id.item1);
                if(checkFavorite(position)){
                    menuItem.setIcon(R.drawable.favorite_filled_icon);
                }
                ////////////////
                //The following lines updates the menu according to the view pager
                //For some reason setting up listener here inside the onCreateMenu works instead of outside
                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        if(checkFavorite(position)){
                            menuItemSong.setIcon(R.drawable.favorite_filled_icon);
                        }else{
                            menuItemSong.setIcon(R.drawable.favorites_icon);
                        }
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.item1){

                    //Changing menu item icon and updating database
                    try(SQLiteOpenHelper sqLiteOpenHelper = new FavoriteDatabase(getContext());
                        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();){
                        int position = viewPager2.getCurrentItem();
                        Cursor cursor = database.query("FAVORITES", new String[]{"_id, IS_FAVORITE"}, "_id = ?", new String[]{Integer.toString(position+1)}, null, null, null);
                        int fav_int = 0;
                        if(cursor.moveToFirst()){
                            fav_int = cursor.getInt(1);
                        }
                        if(fav_int == 1){
                            menuItem.setIcon(R.drawable.favorites_icon);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("IS_FAVORITE", 0);
                            database.update("FAVORITES", contentValues, "_id = ?", new String[]{Integer.toString(position+1)});
                        }else{
                            menuItem.setIcon(R.drawable.favorite_filled_icon);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("IS_FAVORITE", 1);
                            database.update("FAVORITES", contentValues, "_id = ?", new String[]{Integer.toString(position+1)});
                        }
                        database.close();
                        cursor.close();
                    }
                }

                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


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

    private boolean checkFavorite(int position) {
        try{
            SQLiteOpenHelper sqLiteOpenHelper = new FavoriteDatabase(getContext());
            SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = database.query("FAVORITES", new String[]{"_id, IS_FAVORITE"}, "_id = ?", new String[]{Integer.toString(position+1)}, null, null, null);
            if(cursor.moveToFirst()){
                if(cursor.getInt(1) == 1){
                    return true;
                }
            }
            database.close();
            cursor.close();
        }catch (Exception e){
            Log.d("Error", "Error");
        }
        return false;
    }

}
