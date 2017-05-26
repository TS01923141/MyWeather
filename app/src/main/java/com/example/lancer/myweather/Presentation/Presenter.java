package com.example.lancer.myweather.Presentation;

import com.example.lancer.myweather.Model.JSON_Data;
import com.example.lancer.myweather.View.MainView;

import retrofit2.Response;

/**
 * Created by Lancer on 2017/5/24.
 */

public interface Presenter {

    void setData(JSON_Data data);
    void setResponse(Response<JSON_Data> response);

    void setRealmObjectData();

    void setView(MainView view);

    void getRealmData();
}