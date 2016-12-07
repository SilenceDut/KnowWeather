package com.silencedut.knowweather.citys.adapter;

import android.content.Context;

import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;

/**
 * Created by SilenceDut on 16/10/22 .
 */

public class CityWeatherAdapter extends BaseRecyclerAdapter {

    boolean mIsDeleting;

    public CityWeatherAdapter(Context context) {
        super(context);
    }

    void setDeleteAction(boolean deleting) {
        this.mIsDeleting = deleting;
    }

}
