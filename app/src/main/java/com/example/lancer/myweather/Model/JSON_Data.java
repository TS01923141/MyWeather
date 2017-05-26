package com.example.lancer.myweather.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lancer on 2017/5/24.
 */

public class JSON_Data extends RealmObject{
    private RealmList<List> list;

    public RealmList<List> getList(){
        return list;
    }
    public void setList(RealmList<List> list){
        this.list = list;
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
