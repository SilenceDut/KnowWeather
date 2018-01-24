package com.silencedut.knowweather.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.AqiData;
import com.silencedut.knowweather.ui.adapter.AqiViewHolder;
import com.silencedut.knowweather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.ui.adapter.DailyWeatherHolder;
import com.silencedut.knowweather.ui.adapter.GuideData;
import com.silencedut.knowweather.ui.adapter.GuideHolder;
import com.silencedut.knowweather.ui.adapter.LifeGuideHolder;
import com.silencedut.knowweather.ui.adapter.LifeIndexData;
import com.silencedut.knowweather.ui.adapter.LifeIndexGuideData;
import com.silencedut.knowweather.ui.adapter.LifeIndexesHolder;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import java.util.List;

import butterknife.BindView;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.weather_info_recyclerView)
    RecyclerView mWeatherItemList;

    private BaseRecyclerAdapter mMoreInfoAdapter;
    private WeatherModel mWeatherModel;

    public static BaseFragment newInstance() {
        WeatherFragment weatherFragment;
        weatherFragment = new WeatherFragment();
        return weatherFragment;
    }

    @Override
    public void initBeforeView() {

        mWeatherModel = ModelProvider.getModel(getActivity(),WeatherModel.class);
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_fragment_main;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);

        mWeatherItemList.setLayoutManager(linearLayoutManager);
        mWeatherItemList.setBackgroundResource(R.color.dark_background);
        mMoreInfoAdapter = new BaseRecyclerAdapter(mActivity);
    }

    @Override
    protected void initDataObserver() {

        mWeatherModel.getGetWeatherStatus().observe(this, new Observer<StatusDataResource.Status>() {
            @Override
            public void onChanged(@Nullable StatusDataResource.Status status) {
                onMoreInfo(mWeatherModel.getAqiData(),mWeatherModel.getDailyData(),mWeatherModel.getLifeIndexData());
            }
        });
    }

    public void onMoreInfo(AqiData aqiData, List<DailyWeatherData> dailyForecastDatas, LifeIndexData lifeIndexData) {

        if (!isVisible() || !isAdded()) {
            return;
        }

        mMoreInfoAdapter.clear();

        if(dailyForecastDatas!=null) {
            GuideData guideData1 = new GuideData(getString(R.string.weather_future_weather));
            mMoreInfoAdapter.registerHolder(GuideHolder.class, guideData1);
            mMoreInfoAdapter.registerHolder(DailyWeatherHolder.class, dailyForecastDatas);

        }

        if(aqiData!=null) {

            GuideData guideData2 = new GuideData(getString(R.string.weather_aqi_guide));
            mMoreInfoAdapter.addData(guideData2);
            mMoreInfoAdapter.registerHolder(AqiViewHolder.class, aqiData);
        }

        if(lifeIndexData!=null) {
            LifeIndexGuideData lifeIndexGuideData = new LifeIndexGuideData(getString(R.string.weather_lifeIndexes));
            mMoreInfoAdapter.registerHolder(LifeGuideHolder.class, lifeIndexGuideData);
            mMoreInfoAdapter.registerHolder(LifeIndexesHolder.class, lifeIndexData);
        }

        mWeatherItemList.setAdapter(mMoreInfoAdapter);
    }
}
