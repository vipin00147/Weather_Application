package com.example.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cityAdapter extends RecyclerView.Adapter<cityAdapter.viewHolder> {

    ArrayList<String> add_city;

    public cityAdapter(ArrayList<String> add_city) {
        this.add_city = add_city;
    }

    public cityAdapter() {

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.city_name.setText(add_city.get(position));
        holder.city_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                add_city.remove(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return add_city.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView weather_icon;
        View view;
        TextView city_name, temperature;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            weather_icon = itemView.findViewById(R.id.weather_icon);
            city_name = itemView.findViewById(R.id.city_name);
            temperature = itemView.findViewById(R.id.temperature);
            view = itemView;
        }
    }
}
