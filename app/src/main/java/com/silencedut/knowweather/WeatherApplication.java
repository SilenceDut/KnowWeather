package com.silencedut.knowweather;

import android.app.Application;

import com.silencedut.weather_core.CoreManager;

;

/**
 * Created by SilenceDut on 16/10/15.
 */

public class WeatherApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        CoreManager.init(this);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Application getContext() {
        return sApplication;
    }


}
