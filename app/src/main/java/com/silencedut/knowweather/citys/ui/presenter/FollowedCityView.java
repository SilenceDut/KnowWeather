package com.silencedut.knowweather.citys.ui.presenter;

import com.silencedut.knowweather.citys.adapter.FollowedCityData;
import com.silencedut.knowweather.common.BaseView;

import java.util.List;

/**
 * Created by SilenceDut on 2016/11/14 .
 */

public interface FollowedCityView extends BaseView {
    void onAllFollowedCities(List<FollowedCityData> followedCityDatas);

    void onFollowedCity(FollowedCityData followedCityData);
}
