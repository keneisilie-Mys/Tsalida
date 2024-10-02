package com.ken.tsalida;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ken.tsalida.adapters.SongTitleAdapter;
import com.ken.tsalida.fragments.EndFragment;
import com.ken.tsalida.fragments.FavoritesFragment;
import com.ken.tsalida.fragments.ListSongFragment;
import com.ken.tsalida.fragments.MoreFragment;
import com.ken.tsalida.fragments.ResponsiveFragment;
import com.ken.tsalida.fragments.SongFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements SongTitleAdapter.Listener {

    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        //Setting the status bar color here since i failed changing it with the theme
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.for_status_bars));


        Toast toast = Toast.makeText(MainActivity.this, "Press Back Again To Exit", Toast.LENGTH_SHORT);
        //On back pressed
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment favoriteFragment2 = getSupportFragmentManager().findFragmentByTag("FavoriteFragment2");

                Fragment responsiveImage = getSupportFragmentManager().findFragmentByTag("ResponsiveImage");
                Fragment responsiveList = getSupportFragmentManager().findFragmentByTag("ResponsiveList");

                Fragment endImage = getSupportFragmentManager().findFragmentByTag("EndImage");
                Fragment endList = getSupportFragmentManager().findFragmentByTag("EndList");

                //For favorites fragment
                if(favoriteFragment2!=null && favoriteFragment2.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FavoritesFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE).commit();
                }
                //For Responsive reading fragments
                else if(responsiveImage != null && responsiveImage.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResponsiveFragment(), "ResponsiveList").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE).commit();
                }
                else if(responsiveList != null && responsiveList.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MoreFragment(), "MoreFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE).commit();
                }
                //For End Pages Fragment
                else if(endImage != null && endImage.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EndFragment(), "EndList").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE).commit();
                }
                else if(endList != null && endList.isVisible()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MoreFragment(), "MoreFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE).commit();
                }

                //Double back to exit
                else{
                    if(backPressedTime + 2000 < System.currentTimeMillis()){
                        toast.show();
                        backPressedTime = System.currentTimeMillis();
                    }else{
                        toast.cancel();
                        finish();
                    }
                }
            }
        });

        //For some reason the selected item and the fragment in the fragment container is retained even though the screen rotates
        if(savedInstanceState !=null){
            bottomNavigationView.setSelectedItemId(R.id.item4); //It didn't matter if this is here, the activity retained the seleted item itself when screen was rotated
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new SongFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.item2);
        }
        //Declaring things to use later


        //Method for responding to navigation clicks
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //getSupportFragmentManager().popBackStack("favFragment2", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                if(item.getItemId() == R.id.item1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ListSongFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    return true;
                } else if (item.getItemId() == R.id.item2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SongFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    return true;
                }else if (item.getItemId() == R.id.item3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FavoritesFragment(), "FavoriteFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit(); //The tag helps find fragments that are added to the backstack
                    return true;
                }else if (item.getItemId() == R.id.item4) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MoreFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    return true;
                }
                return true;
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

    }

    //The following onSaveInstanceState lines are redundant
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        int bottomId = bottomNavigationView.getSelectedItemId();
        outState.putInt("BottomItem", bottomId);
    }

    ///////Get the position directly from the adapter
    @Override
    public void onClickViewHolder(int songIndex) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.item2); //If it comes after replacing the fragment, this method will call the onNavigationItemSelected again
        int adjustedIndex = adjustIndex(songIndex);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SongFragment(adjustedIndex)).commit();
    }

    //Method to adjust the index of the song
    public int adjustIndex(int pos){
        if(pos >11 && pos <17){
            return pos-1;
        } else if (pos >16 && pos <30) {
            return pos-2;
        }else if (pos >29 && pos <39) {
            return pos-3;
        }else if (pos >38 && pos <56) {
            return pos-4;
        }else if (pos >55 && pos <75) {
            return pos-5;
        }else if (pos >74 && pos <88) {
            return pos-6;
        }else if (pos >87 && pos <97) {
            return pos-7;
            //////
        } else if (pos > 96 && pos <104) {
            return pos - 6;
        }else if (pos > 103 && pos <113) {
            return pos-7;
        }else if (pos > 112 && pos <126 || pos == 127) {
            return pos-8;
        }//////////////////
        else if (pos ==126 || pos > 127 && pos<135 ) {
            return pos - 9;
        } else if (pos > 134 && pos <146) {
            return pos - 10;
        }else if (pos > 145 && pos <160) {
            return pos - 11;
        }else if (pos > 159 && pos <166) {
            return pos - 12;
            ///////////////////
        }else if (pos > 165 && pos <203) {
            return pos - 11;////correct
        }else if (pos > 202 && pos <209) {
            return pos - 12;
        }else if (pos > 208 && pos <219) {
            return pos - 13;
        }else if (pos > 218 && pos <224) {
            return pos - 14;
        }else if (pos >223  && pos <239) {
            return pos - 13;///////////
        }else if (pos > 238 && pos <244) {
            return pos - 12;
        }else if (pos > 243 && pos <249) {
            return pos - 11;
        }else if (pos > 248 && pos <254) {
            return pos - 10;
        }else if (pos > 253 && pos <261) {
            return pos - 11;
        }else if (pos > 260 && pos <264) {
            return pos - 10;
        }else if (pos > 263 && pos <267) {
            return pos - 11;
        }else if (pos > 266 && pos <270) {
            return pos - 10;
        }else if (pos > 269 && pos <275) {
            return pos - 11;
        }else if (pos > 274 && pos <292) {
            return pos - 12;
        }else if (pos > 291 && pos <305) {
            return pos - 13;
        }else if (pos > 304 && pos <313) {
            return pos - 14;
        }else if (pos > 312 && pos <323) {
            return pos - 15;
        }else if (pos > 322 && pos <374) {
            return pos - 14;
        }else if (pos > 373 && pos <381) {
            return pos - 15;
        }else if (pos > 380 && pos <383) {
            return pos - 16;
        }else if (pos > 382 && pos <385) {
            return pos - 17;
        }else if (pos > 384 && pos <387) {
            return pos - 18;
        }else if (pos > 386 && pos <389) {
            return pos - 19;
        }else if (pos > 388 && pos <394) {
            return pos - 20;
        }else if (pos > 393 && pos <398) {
            return pos - 21;
        }else if (pos > 397 && pos <400) {
            return pos - 22;
        }else if (pos > 399 && pos <402) {
            return pos - 23;
        }else if (pos > 401 && pos <405) {
            return pos - 24;
        }else if (pos > 404 && pos <408) {
            return pos - 25;
        }else if (pos > 407 && pos <411) {
            return pos - 26;
        }else if (pos > 410 && pos <413) {
            return pos - 27;
        }else if (pos > 412 && pos <418) {
            return pos - 28;
        }else if (pos > 417 && pos <422) {
            return pos - 27;
        }
        return pos;
    }

}