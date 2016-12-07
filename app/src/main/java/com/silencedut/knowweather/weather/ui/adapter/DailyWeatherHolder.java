package com.silencedut.knowweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.common.Constants;

import butterknife.BindView;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class DailyWeatherHolder extends BaseViewHolder<DailyWeatherData> {

    @BindView(R.id.date_week)
    TextView dateWeek;
    @BindView(R.id.weather_status_daily)
    TextView weatherStatusDaily;
    @BindView(R.id.weather_icon_daily)
    ImageView weatherIconDaily;
    @BindView(R.id.temp_daily)
    TextView tempDaily;

    public DailyWeatherHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(DailyWeatherData data, int position) {
        WeatherEntity.DailyForecastEntity dailyForecastData = data.dailyForecastData;
        if (Check.isNull(dailyForecastData)) {
            return;
        }
        dateWeek.setText(dailyForecastData.getWeek());
        weatherStatusDaily.setText(dailyForecastData.getWeather());
        tempDaily.setText(dailyForecastData.getTemp_range());
        weatherIconDaily.setImageResource(Constants.getIconId(dailyForecastData.getWeather()));
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_daily_forecast;
    }

}
