package com.example.lancer.myweather.View;

import com.example.lancer.myweather.Model.JSON_Data;

import java.util.List;

/**
 * Created by Lancer on 2017/5/24.
 */

public interface MainView {

    void setAdapterData(List<JSON_Data> data);
}
