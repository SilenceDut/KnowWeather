package com.silencedut.knowweather.citys.adapter;

import android.support.v4.util.Pair;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HeaderData implements BaseAdapterData {


    private List<Pair<String, String>> mHotCities;

    public HeaderData() {
        mHotCities = new ArrayList<>();
        mHotCities.add(new Pair<>("北京", "101010100"));
        mHotCities.add(new Pair<>("上海", "101020100"));
        mHotCities.add(new Pair<>("广州", "101280101"));
        mHotCities.add(new Pair<>("深圳", "101280601"));
        mHotCities.add(new Pair<>("杭州", "101210101"));
        mHotCities.add(new Pair<>("南京", "101190101"));
        mHotCities.add(new Pair<>("武汉", "101200101"));
        mHotCities.add(new Pair<>("重庆", "101040100"));

    }

    List<Pair<String, String>> getCities() {
        return mHotCities;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_city_select_header;
    }
}
