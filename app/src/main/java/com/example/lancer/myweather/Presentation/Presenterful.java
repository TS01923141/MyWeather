package com.example.lancer.myweather.Presentation;

import com.example.lancer.myweather.Model.JSON_Data;

import retrofit2.Response;

/**
 * Created by Lancer on 2017/5/24.
 */
/*
data為Realm資料庫
response為從webApi獲取的結果
在此將從webApi獲取的結果存入資料庫
 */
public class Presenterful implements Presenter{

    private JSON_Data data;
    private Response<JSON_Data> response;

    @Override
    public void setData(JSON_Data data) {
        this.data = data;
    }

    @Override
    public void setResponse(Response<JSON_Data> response) {
        this.response = response;
    }

    @Override
    public void setRealmObjectData() {
        int i;
        for (i=0;i<data.getList().size();i++){
            data.getList().get(i).getTemp().setDay(response.body().getList().get(i).getTemp().getDay());
            data.getList().get(i).getTemp().setMax(response.body().getList().get(i).getTemp().getMax());
            data.getList().get(i).getTemp().setMin(response.body().getList().get(i).getTemp().getMin());

            data.getList().get(i).getWeather().get(0).setDescription(response.body().getList().get(i).getWeather().get(0).getDescription());
            data.getList().get(i).getWeather().get(0).setIcon(response.body().getList().get(i).getWeather().get(0).getIcon());
            data.getList().get(i).getWeather().get(0).setMain(response.body().getList().get(i).getWeather().get(0).getMain());
            data.getList().get(i).getWeather().get(0).setId(response.body().getList().get(i).getWeather().get(0).getId());
        }
    }
}