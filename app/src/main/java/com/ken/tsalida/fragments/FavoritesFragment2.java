package com.ken.tsalida.fragments;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ken.tsalida.DepthPageTransformer;
import com.ken.tsalida.FavoriteDatabase;
import com.ken.tsalida.adapters.ImageAdapterr;
import com.ken.tsalida.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment2 extends Fragment {
    private int position;
    private List<Integer> favImage = new ArrayList<>();

    public FavoritesFragment2(){}

    public FavoritesFragment2(int positionOnList){
        this.position = positionOnList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites2, container, false);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerFav);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
        bottomNavigationView.animate().translationY(0).setDuration(300);

        AppBarLayout appBarLayout = activity.findViewById(R.id.appBarLayout);
        if(appBarLayout!=null){
            appBarLayout.setExpanded(true, true);
        }

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.fav_toolbar_second, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_frm_left, R.anim.fade_out).replace(R.id.fragmentContainer, new FavoritesFragment()).commit();
                }
                if(menuItem.getItemId() == R.id.item1){
                    int position = viewPager2.getCurrentItem();
                    int resId = favImage.get(position);

                    //Writing bitmap to the folder created in the cache
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
                    File cachePath = new File(getContext().getCacheDir(), "images");
                    cachePath.mkdirs();
                    File file = new File(cachePath, "image.jpg");
                    try(FileOutputStream stream = new FileOutputStream(file)){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    }catch(Exception e){
                        Log.d("Error bruh", "error");
                    }

                    //Using content provider(File provider to get the uri of image)
                    Uri content = FileProvider.getUriForFile(getContext(), getContext().getPackageName()+".fileprovider", file);
                    //Finally make the intent
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setDataAndType(content, getContext().getContentResolver().getType(content));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_STREAM, content);
                    startActivity(Intent.createChooser(intent, "Share using"));
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        try(SQLiteOpenHelper openHelper = new FavoriteDatabase(getContext());
            SQLiteDatabase database = openHelper.getReadableDatabase()){

            Cursor cursor = database.query("FAVORITES", new String[]{"_id, IS_FAVORITE"}, "IS_FAVORITE = ?", new String[]{"1"}, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    int num = cursor.getInt(0);
                    String itemName = "page"+num;
                    int resId = getResources().getIdentifier(itemName, "drawable", getContext().getPackageName());
                    favImage.add(resId);
                }while(cursor.moveToNext());
            }

        }
        ImageAdapterr imageAdapterr = new ImageAdapterr(favImage);
        viewPager2.setAdapter(imageAdapterr);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.setCurrentItem(position, false);

        return view;
    }
}