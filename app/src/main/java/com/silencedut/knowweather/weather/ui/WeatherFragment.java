package com.silencedut.knowweather.weather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.BaseFragment;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.weather.ui.adapter.AqiData;
import com.silencedut.knowweather.weather.ui.adapter.AqiViewHolder;
import com.silencedut.knowweather.weather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.weather.ui.adapter.DailyWeatherHolder;
import com.silencedut.knowweather.weather.ui.adapter.GuideData;
import com.silencedut.knowweather.weather.ui.adapter.GuideHolder;
import com.silencedut.knowweather.weather.ui.adapter.LifeGuideHolder;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexData;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexGuideData;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexesHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mWeatherItemList;

    private BaseRecyclerAdapter mMoreInfoAdapter;

    public static WeatherFragment newInstance() {
        WeatherFragment weatherFragment;
        weatherFragment = new WeatherFragment();
        return weatherFragment;
    }

    @Override
    public void initBeforeView() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.common_recyclerview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);

        mWeatherItemList.setLayoutManager(linearLayoutManager);
        mWeatherItemList.setBackgroundResource(R.color.dark_background);
        mMoreInfoAdapter = new BaseRecyclerAdapter(mActivity);

    }

    public void onMoreInfo(AqiData aqiData, List<DailyWeatherData> dailyForecastDatas, LifeIndexData lifeIndexData) {

        if (!isVisible() || !isAdded()) {
            return;
        }

        mMoreInfoAdapter.clear();
        GuideData guideData = new GuideData(getString(R.string.future_weather));
        mMoreInfoAdapter.registerHolder(GuideHolder.class, guideData);
        mMoreInfoAdapter.registerHolder(DailyWeatherHolder.class, dailyForecastDatas);

        guideData = new GuideData(getString(R.string.aqi_guide));
        //guideData.setGuideIconId(R.mipmap.question);
        mMoreInfoAdapter.addData(guideData);
        mMoreInfoAdapter.registerHolder(AqiViewHolder.class, aqiData);

        LifeIndexGuideData lifeIndexGuideData = new LifeIndexGuideData(getString(R.string.lifeIndexes));
        mMoreInfoAdapter.registerHolder(LifeGuideHolder.class, lifeIndexGuideData);
        mMoreInfoAdapter.registerHolder(LifeIndexesHolder.class, lifeIndexData);

        mWeatherItemList.setAdapter(mMoreInfoAdapter);
    }
}
