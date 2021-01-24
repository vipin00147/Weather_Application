package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class City_List extends AppCompatActivity {

    RecyclerView recyclerView;
    CityAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city__list);

        loadData();
        buildRecyclerView();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Model.add_city.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());

                saveData();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("City List", MODE_PRIVATE);
        Gson gson = new Gson();
        String json1 = sharedPreferences.getString("myCityList", null);
        String json2 = sharedPreferences.getString("myTempList", null);
        String json3 = sharedPreferences.getString("myIconList", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        Model.add_city = gson.fromJson(json1, type);
        Model.temperature = gson.fromJson(json2, type);
        Model.icon = gson.fromJson(json3, type);

        if (Model.add_city == null) {
            Model.add_city = new ArrayList<String>();
            Model.temperature = new ArrayList<String>();
            Model.icon = new ArrayList<String>();
        }
    }
    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.location_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CityAdapter(Model.add_city, Model.temperature, Model.icon);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("City List", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json1 = gson.toJson(Model.add_city);
        editor.putString("myCityList", json1);
        String json2 = gson.toJson(Model.temperature);
        editor.putString("myTempList", json2);
        String json3 = gson.toJson(Model.icon);
        editor.putString("myIconList", json3);
        editor.apply();
    }
}