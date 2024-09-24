package com.example.tsalida.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tsalida.R;
import com.jsibbold.zoomage.ZoomageView;

public class MoreImagesAdapter extends RecyclerView.Adapter<MoreImagesAdapter.ViewHolder> {
    int[] imageIds;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ZoomageView zv;
        ViewHolder(ZoomageView zoomageView){
            super(zoomageView);
            zv = zoomageView;
        }
    }
    public MoreImagesAdapter(int[] imageIds){
        this.imageIds = imageIds;
    }

    @Override
    public int getItemCount() {
        return imageIds.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ZoomageView zoomageView = (ZoomageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.zoom_image, parent, false);
        return new ViewHolder(zoomageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZoomageView zoomageView = holder.zv;
        zoomageView.setImageResource(imageIds[position]);
    }
}
