package com.example.lancer.myweather;

import com.example.lancer.myweather.Model.JSON_Data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lancer on 2017/5/23.
 */


public class MyRetrofit {

    public interface MyDataService{
        @GET("/data/2.5/forecast/daily")
        Call<JSON_Data> getData(@Query("q")String city, @Query("APPID")String apiKey, @Query("units")String units, @Query("cnt")String cnt);
    }


    // 以Singleton模式建立
    private static MyRetrofit mInstance = new MyRetrofit();
    private MyDataService myDataService;
    private MyRetrofit(){
        // 設置baseUrl即要連的網站，addConverterFactory用Gson作為資料處理Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myDataService = retrofit.create(MyDataService.class);
    }

    public static MyRetrofit getmInstance(){
        return mInstance;
    }
    public MyDataService getAPI(){
        return myDataService;
    }
}
