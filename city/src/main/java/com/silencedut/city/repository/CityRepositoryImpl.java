package com.silencedut.city.repository;

import android.os.Handler;
import android.support.annotation.WorkerThread;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.baselib.commonhelper.persistence.FileHelper;
import com.silencedut.baselib.commonhelper.persistence.PreferencesHelper;
import com.silencedut.baselib.commonhelper.utils.JsonHelper;
import com.silencedut.city.repository.db.CityDatabase;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.repository.DBHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SilenceDut on 2018/1/5 .
 */

@HubInject(api = ICityRepositoryApi.class)
public class CityRepositoryImpl implements ICityRepositoryApi {
    private static final String TAG = "CityRepositoryImpl";
    private static final String CITY_INITED = "CITY_INITED";
    private static final String CITY_DB_NAME = "china_city";
    private static final String CURRENT_CITY = "CURRENT_CITY";
    private CityDatabase mCityDatabase;
    private Handler mCityHandler;
    private List<City> mAllCityData = new ArrayList<>();

    public CityRepositoryImpl() {
        mCityDatabase = DBHelper.provider(CityDatabase.class,CITY_DB_NAME);
        mCityHandler =  TaskScheduler.provideHandler(TAG);

    }


    @Override
    public void saveCurrentCityId(String cityId) {
        PreferencesHelper.put(CURRENT_CITY,cityId);
    }

    @Override
    public String getCurrentCityId() {
        return PreferencesHelper.get(CURRENT_CITY,CURRENT_CITY);
    }

    @Override
    public boolean hadCurrentCityId() {
        return !getCurrentCityId().equals(CURRENT_CITY);
    }


    @Override
    public void loadCitys() {
        boolean cityInited = PreferencesHelper.get(CITY_INITED, false);
        if (cityInited) {
            return;
        }

        mCityHandler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    String citys = FileHelper.assetFile2String("china_citys.txt", CoreManager.getContext());
                    JSONArray jsonArray = new JSONArray(citys);
                    List<City>  allCitys = new ArrayList<>();

                    for(int index =0 ; index < jsonArray.length() ;index++) {
                        JSONObject cityObject = jsonArray.getJSONObject(index);
                        CityEntry cityEntry = JsonHelper.fromJson(cityObject.toString(), CityEntry.class);

                        for(CityEntry.CityBean cityBean : cityEntry.getCity()) {
                            for(CityEntry.CityBean.CountyBean county : cityBean.getCounty()) {
                                City city = new City();
                                city.province = cityEntry.getName();
                                city.provinceEn = cityEntry.getName_en();
                                city.cityName = cityBean.getName();
                                city.cityId = county.getCode();
                                city.country = county.getName();
                                city.countryEn = county.getName_en();

                                allCitys.add(city);
                            }
                        }
                    }

                    Collections.sort(allCitys, new CityComparator());

                    mCityDatabase.cityDao().insertCities(allCitys);

                    PreferencesHelper.get(CITY_INITED, true);

                } catch (Exception e) {
                    LogHelper.error(TAG,"parse city info fail , %s",e);
                }

            }
        });
    }

    /**
     * 通过名字或者拼音搜索
     *
     * @param cityName 市,county 县
     * @return 结果
     */
    @Override
    @WorkerThread
    public City searchCity(final String cityName,final String county) {
        return mCityDatabase.cityDao().searchCity(cityName,county);
    }

    @Override
    @WorkerThread
    public City searchCity(final String cityId) {
        return mCityDatabase.cityDao().searchCity(cityId);
    }

    @Override
    @WorkerThread
    public List<City> matchingCity(String keyword) {
        return  mCityDatabase.cityDao().matchCity(keyword);
    }

    @Override
    public Handler getCityWorkHandler() {
        return mCityHandler;
    }

    @Override
    @WorkerThread
    public List<City> queryAllCities() {
        if(mAllCityData.size() > 0){
            return mAllCityData;
        }
        mAllCityData = mCityDatabase.cityDao().getAll();
        return mAllCityData;
    }

    /**
     * a-z排序
     */
    private class CityComparator implements Comparator<City> {

        @Override
        public int compare(City cityLeft, City cityRight) {

            char a = cityLeft.countryEn.charAt(0);
            char b = cityRight.countryEn.charAt(0);

            return a-b;
        }
    }
}
