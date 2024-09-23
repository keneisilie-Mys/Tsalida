package com.example.tsalida;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MoreListAdapter extends RecyclerView.Adapter<MoreListAdapter.ViewHolder> {
    Context context;

    String[] titles;
    public MoreListAdapter(Context context, String[] titles){
        this.titles = titles;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ViewHolder(CardView cardView){
            super(cardView);
            cv = cardView;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.more_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cv;
        TextView index = cardView.findViewById(R.id.index);
        TextView title = cardView.findViewById(R.id.angami_title);

        index.setText(Integer.toString(position+1));
        title.setText(titles[position]);

        cardView.setOnClickListener(view -> {
            Fragment fragment = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentByTag("ResponsiveFrag");
            if(fragment!=null){
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ResponsiveReadingImageFragment(position)).commit();
            }else{
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EndImageFragment(position)).commit();
            }
        });
    }
}
