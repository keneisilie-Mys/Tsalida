package com.example.tsalida;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jsibbold.zoomage.ZoomageView;

public class ImageAdapterr extends RecyclerView.Adapter<ImageAdapterr.ViewHolder> {
    private int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ZoomageView zoomageView;
        public ViewHolder(ZoomageView v){
            super(v);
            zoomageView = v;
        }
    }

    //The constructor for the outer class
    public ImageAdapterr(int[] imageIds){
        this.imageIds = imageIds;
    }

    //Overriding the item count
    @Override
    public int getItemCount() {
        return imageIds.length;
    }

    //The following code is called when a view needs a new view holder
    @NonNull
    @Override
    public ImageAdapterr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ZoomageView iv = (ZoomageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.zoom_image, parent, false);
        return new ViewHolder(iv);
    }

    //The following code is called when a view need to bind the data to the holders
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZoomageView zoomageView = holder.zoomageView;

        zoomageView.setImageResource(imageIds[position]);
    }
}
