package com.silencedut.weather_core.location;

import com.silencedut.weather_core.api.BaseCoreApi;
import com.silencedut.weather_core.api.cityprovider.City;

/**
 * Created by SilenceDut on 2018/1/8 .
 */

public interface ILocationApi extends BaseCoreApi {


    void startLocation();
    String getLocatedCityId();
    City getLocatedCity();


}
