package com.example.lancer.myweather.Model;

import com.example.lancer.myweather.R;

/**
 * Created by Lancer on 2017/5/30.
 */
//依IconID回傳圖片
public class ImageCheck {
    public int getIconName(String iconName){
        switch (iconName){
            case "01d":
                return R.drawable.m01d;
            case "02d":
                return R.drawable.m02d;
            case "03d":
                return R.drawable.m03d;
            case "04d":
                return R.drawable.m04d;
            case "09d":
                return R.drawable.m09d;
            case "10d":
                return R.drawable.m10d;
            case "11d":
                return R.drawable.m11d;
            case "13d":
                return R.drawable.m13d;
            case "50d":
                return R.drawable.m50d;
            case "01n":
                return R.drawable.m01n;
            case "02n":
                return R.drawable.m02n;
            case "03n":
                return R.drawable.m03n;
            case "04n":
                return R.drawable.m04n;
            case "09n":
                return R.drawable.m09n;
            case "10n":
                return R.drawable.m10n;
            case "11n":
                return R.drawable.m11n;
            case "13n":
                return R.drawable.m13n;
            default:
                return R.drawable.m50n;
        }
    }
}
