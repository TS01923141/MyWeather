package com.example.lancer.myweather.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lancer on 2017/5/26.
 */

public class DataModel extends RealmObject {
    private JSON_Data json_data;
    public JSON_Data getJson_data(){
        return json_data;
    }
    public void setJson_data(JSON_Data json_data){
        this.json_data=json_data;
    }

    @PrimaryKey
    private String location;
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location=location;
    }
}
