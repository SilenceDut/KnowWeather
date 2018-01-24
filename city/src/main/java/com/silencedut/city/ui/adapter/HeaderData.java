package com.silencedut.city.ui.adapter;

import android.support.v4.util.Pair;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.city.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HeaderData implements BaseAdapterData {


    private List<Pair<String, String>> mHotCities;

    public HeaderData() {
        mHotCities = new ArrayList<>();
        mHotCities.add(new Pair<>("北京", "CN101010100"));
        mHotCities.add(new Pair<>("上海", "CN101020100"));
        mHotCities.add(new Pair<>("广州", "CN101280101"));
        mHotCities.add(new Pair<>("深圳", "CN101280601"));
        mHotCities.add(new Pair<>("杭州", "CN101210101"));
        mHotCities.add(new Pair<>("南京", "CN101190101"));
        mHotCities.add(new Pair<>("武汉", "CN101200101"));
        mHotCities.add(new Pair<>("重庆", "CN101040100"));

    }

    List<Pair<String, String>> getCities() {
        return mHotCities;
    }

    @Override
    public int getContentViewId() {
        return R.layout.city_item_city_select_header;
    }
}
