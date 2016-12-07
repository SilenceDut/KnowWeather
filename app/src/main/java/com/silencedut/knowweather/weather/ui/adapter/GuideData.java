package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;

/**
 * Created by SilenceDut on 16/10/22.
 */

public class GuideData implements BaseAdapterData {
    public String guide;
    public int guideIconId;

    public GuideData(String guide) {
        this.guide = guide;
    }

    public void setGuideIconId(int guideIconId) {
        this.guideIconId = guideIconId;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_guide;
    }
}
