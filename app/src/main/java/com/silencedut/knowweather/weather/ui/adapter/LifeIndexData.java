package com.silencedut.knowweather.weather.ui.adapter;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class LifeIndexData implements BaseAdapterData {

    public List<WeatherEntity.LifeIndexEntity> lifeIndexesData;

    public LifeIndexData(List<WeatherEntity.LifeIndexEntity> lifeIndexesData) {
        this.lifeIndexesData = lifeIndexesData;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_life;
    }
}
