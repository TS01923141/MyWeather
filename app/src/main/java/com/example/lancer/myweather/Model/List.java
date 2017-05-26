package com.example.lancer.myweather.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Lancer on 2017/5/24.
 */

public class List extends RealmObject{
    private Temp temp;
    private RealmList<Weather> weather;

    public Temp getTemp(){
        return temp;
    }
    public void setTemp(Temp temp){
        this.temp = temp;
    }

    public RealmList<Weather> getWeather(){
        return weather;
    }
    public void setWeather(RealmList<Weather> weather){
        this.weather = weather;
    }

}
