package com.example.practice;

import java.util.ArrayList;

public class Model {
    public static String icon, temperature;
    public  static ArrayList<String> add_city = new ArrayList<>();

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
