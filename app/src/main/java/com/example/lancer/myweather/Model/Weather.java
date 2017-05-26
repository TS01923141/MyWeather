package com.example.lancer.myweather.Model;

import io.realm.RealmObject;

/**
 * Created by Lancer on 2017/5/24.
 */

public class Weather extends RealmObject{
    private int id;private String main;private String description;private String icon;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getMain(){
        return main;
    }
    public void setMain(String main){
        this.main = main;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
        this.icon =icon;
    }

}
/*
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

 */