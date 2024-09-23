package com.example.tsalida;

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

public class ResponsiveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_responsive, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Responsive Reading");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MoreFragment()).commit();
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        String[] titles = {"Mha pete Chükeshüu Ukepenuopfü",
                "Kethezie Khakeshü Kekie",
                "Seyie Pete Niepuu Ketshe",
                "Thezho Kerko",
                "Messiah Kediu",
                "Khrista Themia Chü Lhoukevor",
                "Mhakesimia Kro Puo Jisu Pfhükevor",
                "Jisu Dzülukepfü",
                "Jisu Meyiemeluo Di Jerusalem Nu Kele",
                "Kepenuopfü Kuonuou",
                "Jisu Se",
                "Jisu Teigei Khoketa",
                "Khrista Zayiekechü",
                "Khrista La Vorketuo",
                "Kemesa Ruopfü",
                "Missions",
                "Pentecost",
                "Khrista Kehou",
                "Kerükra Ca Kecü",
                "Thejakepfüthoko",
                "Khubebo Botho-u mu Puocoko",
                "Kechado Thakeshü",
                "Siedzikechü",
                "Thenou Thukepeluo Die",
                "Penuokesakecü",
                "Khrukhrekecü",
                "Kecha",
                "Ruopfü Rhütho",
                "Mha khapie Niepuu Ketsü",
                "Khristamia Kekhrie",
                "Khristamia Rheisietaketuo",
                "Teigei Kinyi"
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewRR);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MoreListAdapter adapter = new MoreListAdapter(getContext(), titles);

        recyclerView.setAdapter(adapter);
        return view;
    }
}