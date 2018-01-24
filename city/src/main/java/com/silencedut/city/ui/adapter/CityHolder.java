package com.silencedut.city.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;
import com.silencedut.city.R;
import com.silencedut.city.R2;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class CityHolder extends BaseViewHolder<CityInfoData> {

    @BindView(R2.id.tv_item_city_letter)
    TextView mTvItemCityLetter;
    @BindView(R2.id.tv_item_city_name)
    TextView mTvItemCityName;

    private String mCityId;

    public CityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(CityInfoData data, int position) {
        mCityId = data.getCityId();

        mTvItemCityName.setText(data.getCityName());
        if (data.getInitial() != null) {
            mTvItemCityLetter.setVisibility(View.VISIBLE);
            mTvItemCityLetter.setText(data.getInitial());
        } else {
            mTvItemCityLetter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.city_item_city;
    }

    @OnClick(R2.id.tv_item_city_name)
    public void onClick() {

        CoreManager.getImpl(IWeatherProvider.class).updateWeather(mCityId);

        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }
}
