package com.silencedut.knowweather.model;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.WeatherApplication;
import com.silencedut.knowweather.citys.adapter.CityInfoData;
import com.silencedut.knowweather.citys.ui.presenter.SearchCityView;
import com.silencedut.knowweather.common.BaseModel;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.db.DBManage;
import com.silencedut.knowweather.model.callbacks.ModelCallback;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.utils.TaskExecutor;
import com.silencedut.router.Router;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class CityModel extends BaseModel {

    private AMapLocationClient mLocationClient;
    private String mCityName = Constants.DEFAULT_STR;
    private String mLocationId = Constants.DEFAULT_STR;
    private String mDefaultId = Constants.DEFAULT_STR;


    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultId = PreferencesUtil.get(Constants.DEFAULT_CITY, Constants.DEFAULT_STR);
        mLocationId = PreferencesUtil.get(Constants.LOCATION, Constants.DEFAULT_STR);
        initLocation();
    }

    private CityModel initLocation() {
        mLocationClient = new AMapLocationClient(WeatherApplication.getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    TaskExecutor.executeTask(new Runnable() {
                        @Override
                        public void run() {
                            String locationCityId = Constants.DEFAULT_CITY_ID;
                            if (aMapLocation.getErrorCode() == 0) {

                                String district = aMapLocation.getDistrict();


                                List<CityInfoData> citysInfo = DBManage.getInstance().searchCity(district);

                                //城市库全名不匹配
                                if (citysInfo.size() == 0) {
                                    district = district.substring(0, 2);
                                }


                                citysInfo = DBManage.getInstance().searchCity(district);
                                mCityName = Constants.DEFAULT_STR;

                                //县级不支持,就找上一级市
                                if (citysInfo.size() == 0) {

                                    district = aMapLocation.getCity();
                                    district = district.substring(0, 2);
                                    citysInfo = DBManage.getInstance().searchCity(district);
                                }

                                if (citysInfo.size() > 0) {
                                    mCityName = citysInfo.get(0).getCityName();
                                    locationCityId = citysInfo.get(0).getCityId();
                                }
                                mLocationId = locationCityId;
                                PreferencesUtil.put(Constants.LOCATION, mLocationId);

                            }
                            Router.instance().getReceiver(ModelCallback.LocationResult.class).onLocationComplete(locationCityId, aMapLocation.getErrorCode() == 0);
                        }
                    },false);
                }
            }
        });
        return this;
    }

    public void startLocation() {
        mLocationClient.startLocation();
    }

    public String getLocationCityId() {
        return mLocationId;
    }

    public String getDefaultId() {
        return mDefaultId;
    }

    public boolean noDefaultCity() {
        return Constants.DEFAULT_STR.equals(mDefaultId);
    }

    public void setDefaultId(String defaultId) {
        mDefaultId = defaultId;
        PreferencesUtil.put(Constants.DEFAULT_CITY, defaultId);
    }

    public String locationCityName() {
        return mCityName;
    }

    public void getAllCities() {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                List<CityInfoData> allCities = DBManage.getInstance().getAllCities();
                Router.instance().getReceiver(SearchCityView.class).onAllCities(allCities);
            }
        },false);
    }

    public void matchCities(final String key) {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                List<CityInfoData> matchedCities = DBManage.getInstance().searchCity(key);
                Router.instance().getReceiver(SearchCityView.class).onMatched(matchedCities);
            }
        },false);
    }

    public void unFollowCity(String cityId) {
        Set<String> defaultFollowed = new HashSet<>();
        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        if (!defaultFollowed.contains(cityId)) {
            return;
        }
        String defaultCityId = getDefaultId();
        if (defaultCityId.equals(cityId)) {
            ModelManager.getModel(WeatherModel.class).updateWeather(getLocationCityId());
        }
        defaultFollowed.remove(cityId);
        PreferencesUtil.remove(cityId);
        PreferencesUtil.put(Constants.FOLLOWED_CITIES, defaultFollowed);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

}
