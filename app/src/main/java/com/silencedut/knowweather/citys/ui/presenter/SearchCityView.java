package com.silencedut.knowweather.citys.ui.presenter;

import com.silencedut.knowweather.citys.adapter.CityInfoData;
import com.silencedut.knowweather.common.BaseView;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/22 .
 */

public interface SearchCityView extends BaseView {
    void onMatched(List<CityInfoData> cityInfoDatas);

    void onAllCities(List<CityInfoData> allInfoDatas);
}
