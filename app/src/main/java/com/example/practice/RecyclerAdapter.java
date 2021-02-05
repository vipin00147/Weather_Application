package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static maes.tech.intentanim.CustomIntent.customType;

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
        holder.imageView.setImageResource(bg_images[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), view_theme.class);
                intent.putExtra("theme_image", bg_images[position]);
                context.startActivity(intent);
                customType(context,"fadein-to-fadeout");
            }
        });
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