package com.silencedut.city.ui.search;

import android.arch.lifecycle.MutableLiveData;

import com.silencedut.city.repository.ICityRepositoryApi;
import com.silencedut.city.ui.adapter.CityInfoData;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

public class SearchModel extends BaseViewModel {

    private MutableLiveData<List<CityInfoData>> mAllCityData = new MutableLiveData<>();

    private MutableLiveData<List<CityInfoData>> mMatchedCityData = new MutableLiveData<>();

    @Override
    protected void onCreate() {

    }

    void getAllCities() {
        CoreManager.getImpl(ICityRepositoryApi.class).getCityWorkHandler().post(new Runnable() {
            @Override
            public void run() {
                List<City>  allCity = CoreManager.getImpl(ICityRepositoryApi.class).queryAllCities();
                if(allCity !=null) {

                    List<CityInfoData> cityInfoDatas = new ArrayList<>();
                    String lastInitial = "";
                    for(City city : allCity) {
                        CityInfoData cityInfoData =  new CityInfoData(city.country,city.countryEn,city.cityId);
                        String currentInitial = city.countryEn.substring(0, 1);
                        if (!lastInitial.equals(currentInitial) ) {
                            cityInfoData.setInitial(currentInitial);
                            lastInitial = currentInitial;
                        }
                        cityInfoDatas.add(cityInfoData);
                    }
                    mAllCityData.postValue(cityInfoDatas);
                }
            }
        });
    }

    MutableLiveData<List<CityInfoData>> getAllCityData() {
        return mAllCityData;
    }

    MutableLiveData<List<CityInfoData>> getMatchedCityData() {
        return mMatchedCityData;
    }


    void matchCities(final String key) {
        TaskScheduler.execute(new Runnable() {
            @Override
            public void run() {
                List<City> allCity = CoreManager.getImpl(ICityRepositoryApi.class).matchingCity(key);
                if(allCity !=null ) {

                    List<CityInfoData> cityInfoDatas = new ArrayList<>();
                    for(City city : allCity) {
                        cityInfoDatas.add(new CityInfoData(city.country,city.countryEn,city.cityId));
                    }
                    mMatchedCityData.postValue(cityInfoDatas);
                }
            }
        });
    }
}
