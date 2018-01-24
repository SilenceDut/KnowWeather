package com.silencedut.city.ui.adapter;

import android.content.Context;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;

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
