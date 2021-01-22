package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    int bg_images[];

    public RecyclerAdapter(Context context, int[] bg_images) {
        this.context = context;
        this.bg_images = bg_images;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.imageView.setBackground(context.getResources().getDrawable(bg_images[position]));
    }

    @Override
    public int getItemCount() {
        return bg_images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView view_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            view_image = (TextView) itemView.findViewById(R.id.text);
        }
    }
}