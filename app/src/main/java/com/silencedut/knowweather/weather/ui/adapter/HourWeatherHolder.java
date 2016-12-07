package com.silencedut.knowweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

import butterknife.BindView;

/**
 * Created by SilenceDut on 16/10/21.
 */

public class HourWeatherHolder extends BaseViewHolder<HoursForecastData> {
    @BindView(R.id.hour)
    TextView hour;
    @BindView(R.id.hour_icon)
    ImageView hourIcon;
    @BindView(R.id.hour_temp)
    TextView hourTemp;

    public HourWeatherHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(HoursForecastData data, int position) {
        WeatherEntity.HoursForecastEntity hoursForecastData = data.hoursForecastData;
        if (Check.isNull(hoursForecastData)) {
            return;
        }
        hour.setText(hoursForecastData.getTime().substring(11, 16));
        hourIcon.setImageResource(Constants.getIconId(hoursForecastData.getWeather()));
        hourTemp.setText(hoursForecastData.getTemp());
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_hour_forecast;
    }

}
