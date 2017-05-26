package com.example.lancer.myweather.Presentation;

import android.util.Log;

import com.example.lancer.myweather.Model.JSON_Data;
import com.example.lancer.myweather.View.MainView;

import java.util.List;

import io.realm.Realm;
import retrofit2.Response;

/**
 * Created by Lancer on 2017/5/24.
 */

public class Presenterful implements Presenter{

    private JSON_Data data;
    private Response<JSON_Data> response;
    private MainView view;

    @Override
    public void setData(JSON_Data data) {
        this.data = data;
    }

    @Override
    public void setResponse(Response<JSON_Data> response) {
        this.response = response;
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void setRealmObjectData() {

        Log.i("answer", String.valueOf(response.body().getList().get(0).getTemp().getDay()));
        Log.i("answer222", String.valueOf(response.body().getList()));
        Log.i("answer333", String.valueOf(data.getList()));
        data.getList().get(0).getTemp().setDay(response.body().getList().get(0).getTemp().getDay());
        data.getList().get(0).getTemp().setMax(response.body().getList().get(0).getTemp().getMax());
        data.getList().get(0).getTemp().setMin(response.body().getList().get(0).getTemp().getMin());

        data.getList().get(0).getWeather().get(0).setDescription(response.body().getList().get(0).getWeather().get(0).getDescription());
        data.getList().get(0).getWeather().get(0).setIcon(response.body().getList().get(0).getWeather().get(0).getIcon());
        data.getList().get(0).getWeather().get(0).setMain(response.body().getList().get(0).getWeather().get(0).getMain());
        data.getList().get(0).getWeather().get(0).setId(response.body().getList().get(0).getWeather().get(0).getId());

        /*int i;
        for (i=0;i<response.body().getList().size();i++){
            Log.i("answer", String.valueOf(response.body().getList().get(i).getTemp().getDay()));
            Log.i("answer222", String.valueOf(response.body().getList()));
            Log.i("answer333", String.valueOf(data.getList()));
            data.getList().get(i).getTemp().setDay(response.body().getList().get(i).getTemp().getDay());
            data.getList().get(i).getTemp().setMax(response.body().getList().get(i).getTemp().getMax());
            data.getList().get(i).getTemp().setMin(response.body().getList().get(i).getTemp().getMin());

            data.getList().get(i).getWeather().get(0).setDescription(response.body().getList().get(i).getWeather().get(0).getDescription());
            data.getList().get(i).getWeather().get(0).setIcon(response.body().getList().get(i).getWeather().get(0).getIcon());
            data.getList().get(i).getWeather().get(0).setMain(response.body().getList().get(i).getWeather().get(0).getMain());
            data.getList().get(i).getWeather().get(0).setId(response.body().getList().get(i).getWeather().get(0).getId());
        }*/
        /*data.getMain().setTemp(response.body().getMain().getTemp());
        data.getMain().setHumidity(response.body().getMain().getHumidity());
        data.getMain().setPressure(response.body().getMain().getPressure());

        data.getCoord().setLat(response.body().getCoord().getLat());
        data.getCoord().setLon(response.body().getCoord().getLon());

        data.getWind().setSpeed(response.body().getWind().getSpeed());
        data.getWind().setDeg(response.body().getWind().getDeg());
        data.getWind().setDirection();*/
    }

    @Override
    public void getRealmData() {
        Realm realm = Realm.getDefaultInstance();
        List<JSON_Data> data = realm.where(JSON_Data.class).findAll();
        view.setAdapterData(data);
    }
}