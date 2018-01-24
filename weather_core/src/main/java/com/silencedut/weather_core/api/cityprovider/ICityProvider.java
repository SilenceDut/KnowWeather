package com.silencedut.weather_core.api.cityprovider;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.WorkerThread;
import android.support.v4.util.Pair;

import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.corebase.BaseFragment;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

public interface ICityProvider extends ICoreApi {
    @WorkerThread
    City searchCity( String cityId);
    @WorkerThread
    City searchCity( String cityName,final String county);
    Handler getCityWorkHandler();
    void navigationSearchActivity(Context context);
    void saveCurrentCityId(String cityId);
    String getCurrentCityId();
    boolean hadCurrentCityId();
    Pair<BaseFragment,Integer> provideCityFragment();
    void loadCitys();

}
