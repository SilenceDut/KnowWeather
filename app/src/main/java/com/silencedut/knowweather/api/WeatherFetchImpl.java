package com.silencedut.knowweather.api;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.knowweather.entity.AqiEntity;
import com.silencedut.knowweather.entity.HeWeather;
import com.silencedut.knowweather.entity.WeatherTransverter;
import com.silencedut.knowweather.repository.WeatherRepository;
import com.silencedut.weather_core.AppHttpClient;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.StatusDataResource;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by SilenceDut on 2018/1/21 .
 */
@HubInject(api = IFetchWeather.class)
public class WeatherFetchImpl implements IFetchWeather {
    private static final String TAG = "WeatherFetchImpl";
    private static final String $ = "$";
    private NetWeatherApi mNetWeatherApi;
    private AtomicReference<String> mStringAtomicReference = new AtomicReference<>($);

    public WeatherFetchImpl() {
        mNetWeatherApi = AppHttpClient.getInstance().getService(NetWeatherApi.class);
    }

    @Override
    public void queryWeather(final String cityId) {
        if(cityId == null || cityId.equals(mStringAtomicReference.get())) {
            return;
        }

        mStringAtomicReference.set(cityId);

        WeatherRepository.getInstance().getWeatherWorkHandler().post(new Runnable() {
            @Override
            public void run() {
                try {

                    WeatherRepository.getInstance().updateWeather(cityId,StatusDataResource.<WeatherData>loading());

                    CoreManager.getImpl(ICityProvider.class).saveCurrentCityId(cityId);

                    Call<HeWeather> weatherEntityCall  = mNetWeatherApi.getWeather(NetWeatherApi.sHeyWeatherKey,cityId);

                    /*
                    和风天气不支持县级空气质量
                     */
                    City currentCity = CoreManager.getImpl(ICityProvider.class).searchCity(cityId);
                    String cityName = cityId;
                    if(currentCity !=null) {
                        cityName = currentCity.cityName;
                    }

                    Call<AqiEntity> aqiEntityCall  = mNetWeatherApi.getAqi(NetWeatherApi.sHeyWeatherKey,cityName);

                    Response<HeWeather> heWeatherResponse = weatherEntityCall.execute();
                    Response<AqiEntity> aqiEntityResponse = aqiEntityCall.execute();
                    if(heWeatherResponse.isSuccessful()) {
                        WeatherData weatherData = WeatherTransverter.convertFromHeWeather(heWeatherResponse.body(),aqiEntityResponse.body());
                        WeatherRepository.getInstance().updateWeather(cityId,StatusDataResource.success(weatherData));
                    }else {
                        LogHelper.error(TAG, "fetchWeather fail,response is %s",heWeatherResponse.errorBody());
                        WeatherRepository.getInstance().updateWeather(cityId,StatusDataResource.<WeatherData>error(heWeatherResponse.errorBody().string()));
                    }
                } catch (Exception e) {

                    LogHelper.error(TAG, "fetchWeather fail , error " +e);
                    WeatherRepository.getInstance().updateWeather(cityId,StatusDataResource.<WeatherData>error("更新失败"));
                }

                mStringAtomicReference.set($);

            }

        });
    }

    @Override
    public void queryWeather(List<String> citys) {

    }
}
