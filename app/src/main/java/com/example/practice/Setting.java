package com.example.practice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Setting extends AppCompatActivity {

    RecyclerView recyclerView;

    int bg_images [] = {R.drawable.one, R.drawable.two, R.drawable.three,
            R.drawable.four, R.drawable.four, R.drawable.six,
            R.drawable.seven, R.drawable.eight, R.drawable.nine,
            R.drawable.ten};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        RecyclerAdapter adapter = new RecyclerAdapter(this, bg_images);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

}