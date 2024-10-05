package com.ken.tsalida.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ken.tsalida.DepthPageTransformer;
import com.ken.tsalida.FavoriteDatabase;
import com.ken.tsalida.adapters.ImageAdapterr;
import com.ken.tsalida.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SongFragment extends Fragment {
    private int position;

    public SongFragment() {
    }

    public SongFragment(int position) {
        this.position = position;
    }

    private MenuItem menuItemSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_song, container, false);

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
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewPager2 viewPager2 = rootView.findViewById(R.id.imageViewPager);
        //Changing the toolbar menu of the main activity
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                //Setting the toolbar for the default page that appears in the view pager
                menuInflater.inflate(R.menu.song_toolbar_menu, menu);
                menuItemSong = menu.findItem(R.id.item1);
                if(checkFavorite(position)){    //This is the position that was passed to the fragment
                    menuItemSong.setIcon(R.drawable.favorite_filled_icon);
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
                        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase()){
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
                if(menuItem.getItemId() == R.id.item2){
                    int position = viewPager2.getCurrentItem();

                    String imageName = "page"+(position+1);
                    int resId = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());

                    //Getting the image from drawable as bitmap
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);

                    //Create a new folder in the cache path
                    File cachePath = new File(getContext().getCacheDir(), "images");
                    cachePath.mkdirs(); //if folder exists this is ignored

                    //Name the image file
                    File file = new File(cachePath, "image.jpg");

                    //Writing to the stream
                    try(FileOutputStream stream = new FileOutputStream(file)){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream); //Save the bitmap as jpg
                    }catch(IOException e){
                        Log.d("Failed", "Failed bruh");
                    }

                    //Get the content Uri using FileProvider
                    Uri contentUri = FileProvider.getUriForFile(getContext(),  getContext().getPackageName() + ".fileprovider", file);

                    //Finally make the intent
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //Granting read permission to other apps

                    intent.setDataAndType(contentUri, getContext().getContentResolver().getType(contentUri));
                    intent.putExtra(Intent.EXTRA_STREAM, contentUri);

                    startActivity(Intent.createChooser(intent, "Share using:"));

                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


        //Getting the ids of the images with their names to an array
        //int[] imageIds = new int[396];
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 1; i <= 396; i++) {
            String itemName = "page" + i;
            int resId = getResources().getIdentifier(itemName, "drawable", getContext().getPackageName());
            imageIds.add(resId);
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
