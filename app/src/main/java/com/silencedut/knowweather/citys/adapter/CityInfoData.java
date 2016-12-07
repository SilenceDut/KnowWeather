package com.silencedut.knowweather.citys.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class CityInfoData implements BaseAdapterData {

    private String mInitial = Constants.DEFAULT_STR;
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
    public int getItemViewType() {
        return R.layout.item_city;
    }
}
