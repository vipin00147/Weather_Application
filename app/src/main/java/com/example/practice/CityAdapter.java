package com.example.practice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;
import static maes.tech.intentanim.CustomIntent.customType;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.viewHolder> {

    private ArrayList<String> add_city;
    private ArrayList<String> temperature;
    private ArrayList<String> icon;
    Context context;
    public Activity mContext;

    public CityAdapter(ArrayList<String> add_city, ArrayList<String> temperature, ArrayList<String> icon, City_List city_list) {
        this.add_city = add_city;
        this.temperature = temperature;
        this.icon = icon;
        context = city_list;
    }

    @NonNull
    @Override
    public CityAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.viewHolder holder, int position) {
        holder.city_name.setText(add_city.get(position));
        holder.temperature.setText(temperature.get(position)+"℃");
        Glide.with(holder.itemView.getContext()).load(icon.get(position)).into(holder.icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyCity",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city", add_city.get(position));
                editor.commit();

                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.putExtra("position",String.valueOf(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return add_city.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView city_name, temperature;
        ImageView icon;
        LinearLayout item_Layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            city_name = itemView.findViewById(R.id.city_name);
            temperature = itemView.findViewById(R.id.temperature);
            icon = itemView.findViewById(R.id.weather_icon);
            item_Layout = itemView.findViewById(R.id.item_layout);
        }
    }
}
