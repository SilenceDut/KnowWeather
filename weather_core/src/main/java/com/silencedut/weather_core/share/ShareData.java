package com.silencedut.weather_core.share;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.weather_core.R;
import com.silencedut.weather_core.corebase.customview.ShareDialog;


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
    public int getContentViewId() {
        return R.layout.core_item_share;
    }
}
