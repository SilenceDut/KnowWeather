package com.silencedut.city.ui.adapter;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.city.R;


/**
 * Created by SilenceDut on 16/10/29.
 */

public class CityInfoData implements BaseAdapterData {

    private String mInitial;
    private String mCityName;
    private String mCityNamePinyin;
    private String mCityId;

    public CityInfoData(String cityName, String cityNamePinyin, String cityId) {
        this.mCityName = cityName;
        this.mCityNamePinyin = cityNamePinyin;
        this.mCityId = cityId;
    }

    public String getInitial() {
        return mInitial;
    }

    public void setInitial(String initial) {
        this.mInitial = initial;
    }


    public String getCityName() {
        return mCityName;
    }


    public String getCityNamePinyin() {
        return mCityNamePinyin;
    }

    public String getCityId() {
        return mCityId;
    }

    @Override
    public int getContentViewId() {
        return R.layout.city_item_city;
    }
}
