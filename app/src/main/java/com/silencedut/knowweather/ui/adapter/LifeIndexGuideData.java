package com.silencedut.knowweather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;

/**
 * Created by SilenceDut on 16/10/17 .
 */

public class LifeIndexGuideData implements BaseAdapterData {

    String guide;

    public LifeIndexGuideData(String guide) {
        this.guide = guide;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_index_guide;
    }
}
