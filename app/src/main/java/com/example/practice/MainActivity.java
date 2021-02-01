package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {

    TextView TEMPERATURE, CITY, DATE, WEATHER_STATUS, MIN_MAX;
    TextView WIND_SPEED, WIND_DIRECTION, FEELS_LIKE, PRESSURE;
    ImageView LIST, ADD_CITY, SLIDER;

    private String api_key = "fce6acc5bde8c051dcd9461aa098addb";
    private String city ;
    private String url;
    String arr[]= new String[] {"N","NNE","NE","ENE","E","ESE", "SE", "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
    int var1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TEMPERATURE = findViewById(R.id.temp);
        CITY = findViewById(R.id.city);
        DATE = findViewById(R.id.date);
        WEATHER_STATUS = findViewById(R.id.weather_status);
        MIN_MAX = findViewById(R.id.min_max);
        LIST = findViewById(R.id.city_list);
        ADD_CITY = findViewById(R.id.add_city);
        WIND_DIRECTION = findViewById(R.id.wind_direction);
        WIND_SPEED = findViewById(R.id.wind_speed);
        FEELS_LIKE = findViewById(R.id.feels_like);
        PRESSURE = findViewById(R.id.pressure);
        SLIDER = findViewById(R.id.slider);

        SharedPreferences sharedPreferences = getSharedPreferences("MyCity",MODE_PRIVATE);
        city = sharedPreferences.getString("city",null);

        if(city == null) {
            startActivity(new Intent(getApplicationContext(), search_city.class));
        }
        else{
            Calendar calendar = Calendar.getInstance();
            String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

            url = "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric"+"&appid="+api_key;
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{

                        JSONArray jsonArray1 = response.getJSONArray("list");
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                        JSONObject object1 = jsonObject1.getJSONObject("main");

                        String temp1 = object1.getString("temp");
                        double x = Double.parseDouble(temp1);
                        int y = (int)x;

                        String min = object1.getString( "temp_min");
                        String max = object1.getString( "temp_max");


                        JSONArray jsonArray2 = jsonObject1.getJSONArray("weather");
                        JSONObject object2 = jsonArray2.getJSONObject(0);
                        String cloud_status = object2.getString("description");

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("wind");
                        String wind_degree = jsonObject2.getString("deg");
                        String wind_speed = jsonObject2.getString("speed");

                        var1 = (int)((Integer.parseInt(wind_degree) / 22.5) + 0.5);

                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(0);
                        Double double_wind = (Double.parseDouble(wind_speed)) * 3.6;

                        TEMPERATURE.setText(String.valueOf(y)+"°c");
                        CITY.setText(city);
                        DATE.setText(current_date);
                        WEATHER_STATUS.setText(cloud_status);
                        MIN_MAX.setText(min+"℃ / "+max+"°c");
                        WIND_DIRECTION.setText(arr[var1 % 16]);
                        WIND_SPEED.setText(df.format(double_wind)+" Km/h");
                        FEELS_LIKE.setText(object1.getString( "feels_like")+"°c");
                        PRESSURE.setText(object1.getString( "pressure")+" mb");

                        String icon = "https://openweathermap.org/img/wn/"+object2.getString("icon")+"@2x.png";

                        Intent intent = getIntent();
                        String pos = intent.getExtras().getString("position");
                        int position = Integer.parseInt(pos);
                        Model.add_city.set(position, city);
                        Model.temperature.set(position, String.valueOf(y));
                        Model.icon.set(position, icon);

                        saveData();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(jsonObjectRequest);

        }

        ADD_CITY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),search_city.class));
                customType(MainActivity.this,"left-to-right");
            }
        });

        SLIDER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Theme.class));
                customType(MainActivity.this,"bottom-to-up");
            }
        });

        LIST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),City_List.class));
                customType(MainActivity.this,"right-to-left");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
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