package com.silencedut.weather_core.location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.router.Router;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;


/**
 * Created by SilenceDut on 2018/1/8 .
 */

@HubInject(api = ILocationApi.class)
public class LocationImpl implements ILocationApi {
    private static final String TAG = "ILocationImpl";
    private AMapLocationClient mLocationClient;
    private City mLocatedCity;

    @Override
    public void onCreate() {
        initLocation();
    }


    private void initLocation() {

        mLocationClient = new AMapLocationClient(CoreManager.getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                LogHelper.info(TAG, "onLocationChanged %s", aMapLocation);
                if (aMapLocation != null) {

                    CoreManager.getImpl(ICityProvider.class).getCityWorkHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            boolean locationSucceed = false;
                            try {
                                String city = aMapLocation.getCity().substring(0,2);
                                String district = aMapLocation.getDistrict().substring(0, 2);

                                mLocatedCity = CoreManager.getImpl(ICityProvider.class).searchCity(city,district);

                                //城市库全名不匹配
                                if (mLocatedCity == null) {
                                    city = city.substring(0,2);
                                    district = district.substring(0,2);
                                    mLocatedCity = CoreManager.getImpl(ICityProvider.class).searchCity(city,district);

                                }

                                locationSucceed = mLocatedCity != null;

                                if(locationSucceed) {
                                    mLocatedCity.latitude = String.valueOf(aMapLocation.getLatitude());
                                    mLocatedCity.longitude = String.valueOf(aMapLocation.getLongitude());
                                }
                            }catch (Exception e) {
                                LogHelper.error(TAG,"location error %s",e);
                            }

                            Router.instance().getReceiver(LocationNotification.class)
                                    .onLocation(locationSucceed, locationSucceed ?mLocatedCity.cityId:"");

                            LogHelper.info(TAG,"located city %s",mLocatedCity);


                        }
                    });

                }
            }
        });
    }

    @Override
    public void startLocation() {
        mLocationClient.startLocation();
    }

    @Override
    public String getLocatedCityId() {
        return mLocatedCity!=null?mLocatedCity.cityId:"$";
    }

    @Override
    public City getLocatedCity() {
        return mLocatedCity;
    }

}
