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

        /*Log.i("answer", String.valueOf(response.body().getList().get(0).getTemp().getDay()));
        Log.i("answerResonse.body", String.valueOf(response.body().getList()));
        Log.i("answerData", String.valueOf(data));
        Log.i("answerData.day", String.valueOf(data.getList().get(0).getTemp().getDay()));*/
        Log.i("responseAnswer1", String.valueOf(response.body().getList().get(0).getTemp().getDay()));
        Log.i("responseAnswer2", String.valueOf(response.body().getList().get(1).getTemp().getDay()));
        Log.i("responseAnswer3", String.valueOf(response.body().getList().get(2).getTemp().getDay()));
        Log.i("ResponseSize", String.valueOf(response.body().getList().size()));
        Log.i("Response", String.valueOf(response.body().getList()));

        /*data.getList().get(0).getTemp().setDay(response.body().getList().get(0).getTemp().getDay());
        data.getList().get(0).getTemp().setMax(response.body().getList().get(0).getTemp().getMax());
        data.getList().get(0).getTemp().setMin(response.body().getList().get(0).getTemp().getMin());

        data.getList().get(0).getWeather().get(0).setDescription(response.body().getList().get(0).getWeather().get(0).getDescription());
        data.getList().get(0).getWeather().get(0).setIcon(response.body().getList().get(0).getWeather().get(0).getIcon());
        data.getList().get(0).getWeather().get(0).setMain(response.body().getList().get(0).getWeather().get(0).getMain());
        data.getList().get(0).getWeather().get(0).setId(response.body().getList().get(0).getWeather().get(0).getId());*/


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
        Log.i("answer", String.valueOf(data.getList()));
        Log.i("answer1", String.valueOf(data.getList().get(0).getTemp().getDay()));
        Log.i("answer2", String.valueOf(data.getList().get(1).getTemp().getDay()));
        Log.i("answer3", String.valueOf(data.getList().get(2).getTemp().getDay()));
    }

    @Override
    public void getRealmData() {
        Realm realm = Realm.getDefaultInstance();
        List<JSON_Data> data = realm.where(JSON_Data.class).findAll();
        view.setAdapterData(data);
    }
}