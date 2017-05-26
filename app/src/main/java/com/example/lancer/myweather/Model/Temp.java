package com.example.lancer.myweather.Model;

import io.realm.RealmObject;

/**
 * Created by Lancer on 2017/5/24.
 */

public class Temp extends RealmObject{
    private double day;private double min;private double max;

    public double getDay(){
        return day;
    }
    public void setDay(double day){
        this.day = day;
    }

    public double getMin(){
        return min;
    }
    public void setMin(double min){
        this.min = min;
    }

    public double getMax(){
        return max;
    }
    public void setMax(double max){
        this.max = max;
    }


}
