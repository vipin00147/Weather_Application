package com.example.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class search_city extends AppCompatActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                checkCity(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private boolean checkCity(String query) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+query+"&units=metric"+"&appid="+"fce6acc5bde8c051dcd9461aa098addb";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    //current city to shared prefernces...
                    SharedPreferences sharedPreferences = getSharedPreferences("MyCity",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("city",query);
                    editor.commit();

                    JSONArray jsonArray1 = response.getJSONArray("list");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                    JSONObject object1 = jsonObject1.getJSONObject("main");

                    String temp1 = object1.getString("temp");
                    double x = Double.parseDouble(temp1);
                    int y = (int)x;

                    JSONArray jsonArray2 = jsonObject1.getJSONArray("weather");
                    JSONObject object2 = jsonArray2.getJSONObject(0);

                    String icon = "https://openweathermap.org/img/wn/"+object2.getString("icon")+"@2x.png";

                    insertItem(query, y, icon);
                    saveData();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(search_city.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(search_city.this, "Invalid City", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        return true;
    }

    private void insertItem(String city, int temp, String icon) {
        if (Model.add_city == null) {
            Model.add_city = new ArrayList<String>();
            Model.temperature = new ArrayList<String>();
            Model.icon = new ArrayList<String>();
        }

        Model.add_city.add(city);
        Model.temperature.add(String.valueOf(temp));
        Model.icon.add(icon);
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