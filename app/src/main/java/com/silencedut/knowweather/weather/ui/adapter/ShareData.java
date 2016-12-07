package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.common.customview.ShareDialog;

/**
 * Created by SilenceDut on 2016/11/16 .
 */

public class ShareData implements BaseAdapterData {

    boolean mIsWeather;
    ShareDialog mShareDialog;

    public ShareData(boolean weather, ShareDialog dialog) {
        this.mIsWeather = weather;
        this.mShareDialog = dialog;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_share;
    }
}
