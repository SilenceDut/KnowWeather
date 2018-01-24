package com.silencedut.weather_core.location;

import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.api.cityprovider.City;

/**
 * Created by SilenceDut on 2018/1/8 .
 */

public interface ILocationApi extends ICoreApi {


    void startLocation();
    String getLocatedCityId();
    City getLocatedCity();


}
