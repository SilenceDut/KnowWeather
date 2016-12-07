package com.silencedut.knowweather.citys.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.model.WeatherModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class CityHolder extends BaseViewHolder<CityInfoData> {

    @BindView(R.id.tv_item_city_letter)
    TextView mTvItemCityLetter;
    @BindView(R.id.tv_item_city_name)
    TextView mTvItemCityName;

    private String mCityId;

    public CityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(CityInfoData data, int position) {
        mCityId = data.getCityId();

        mTvItemCityName.setText(data.getCityName());
        if (!Constants.DEFAULT_STR.equals(data.getInitial())) {
            mTvItemCityLetter.setVisibility(View.VISIBLE);
            mTvItemCityLetter.setText(data.getInitial());
        } else {
            mTvItemCityLetter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_city;
    }

    @OnClick(R.id.tv_item_city_name)
    public void onClick() {

        ModelManager.getModel(WeatherModel.class).updateWeather(mCityId);

        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }
}
