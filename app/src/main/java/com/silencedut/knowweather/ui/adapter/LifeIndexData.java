package com.silencedut.knowweather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class LifeIndexData implements BaseAdapterData {

    public List<WeatherData.LifeIndexEntity> lifeIndexesData;

    public LifeIndexData(List<WeatherData.LifeIndexEntity> lifeIndexesData) {
        this.lifeIndexesData = lifeIndexesData;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_life;
    }
}
