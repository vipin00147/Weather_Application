package com.example.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class search_city extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    public  static ArrayList<String> add_city;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.location_list);
        add_city = new ArrayList<>();

        loadData();
        setAdapter();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyCity",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city",query);
                editor.commit();


                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                add_city.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        if(add_city.isEmpty()){
            SharedPreferences preferences = getSharedPreferences("MyCity",MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences.edit();
            editor1.putString("city",null);
            editor1.commit();
        }

    }

    private void setAdapter() {
        adapter = new cityAdapter(add_city);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("City List",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("myCityList", null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        add_city = gson.fromJson(json, type);
    }
}