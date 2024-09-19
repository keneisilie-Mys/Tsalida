package com.example.tsalida;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jsibbold.zoomage.ZoomageView;

import java.util.List;

public class ImageAdapterr extends RecyclerView.Adapter<ImageAdapterr.ViewHolder> {
    //private int[] imageIds;

    private List<Integer> imageids2;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ZoomageView zoomageView;
        public ViewHolder(ZoomageView v){
            super(v);
            zoomageView = v;
        }
    }

    //The constructor for the outer class
    public ImageAdapterr(List<Integer> imageIds){
        this.imageids2 = imageIds;
        //this.imageIds = imageIds;
    }

    //Overriding the item count
    @Override
    public int getItemCount() {
        return imageids2.size();
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

        zoomageView.setImageResource(imageids2.get(position));
    }

    public List<Integer> getImageRes(){
        return imageids2;
    }
}
