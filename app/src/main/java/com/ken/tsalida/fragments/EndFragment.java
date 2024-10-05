package com.ken.tsalida.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ken.tsalida.adapters.MoreListAdapter;
import com.ken.tsalida.R;

public class EndFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_responsive, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("End Pages");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_frm_left, R.anim.fade_out).replace(R.id.fragmentContainer, new MoreFragment()).commit();
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        String[] titles = {"Khristamia Zanu Kemezhieshüketuo La Kehoumia Phrakeshü Die",
                "Apostelko Kepele",
                "Niepuu Kecha",
                "Thekha Kecha"
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewRR);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MoreListAdapter adapter = new MoreListAdapter(getContext(), titles);

        recyclerView.setAdapter(adapter);
        return view;
    }
}