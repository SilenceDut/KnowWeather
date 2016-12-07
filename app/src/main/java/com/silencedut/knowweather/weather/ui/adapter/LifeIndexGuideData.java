package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;

/**
 * Created by SilenceDut on 16/10/17 .
 */

public class LifeIndexGuideData implements BaseAdapterData {

    String guide;

    public LifeIndexGuideData(String guide) {
        this.guide = guide;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_index_guide;
    }
}
