package com.silencedut.knowweather.citys.adapter;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.model.CityModel;
import com.silencedut.knowweather.model.WeatherModel;
import com.silencedut.knowweather.model.callbacks.ModelCallback;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HeaderHolder extends BaseViewHolder<HeaderData> implements ModelCallback.LocationResult {

    @BindView(R.id.tv_located_city)
    TextView mTvLocatedCity;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private boolean mLocationSucceeded;

    private CityModel mCityModel;
    private WeatherModel mWeatherModel;

    private BaseRecyclerAdapter mHotCityAdapter;

    @Override
    public void initBeforeView() {
        super.initBeforeView();
        mCityModel = ModelManager.getModel(CityModel.class);
        mWeatherModel = ModelManager.getModel(WeatherModel.class);
    }

    public HeaderHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);

    }

    @Override
    public void updateItem(HeaderData data, int position) {
        if (Check.isNull(data)) {
            return;
        }
        mHotCityAdapter.clear();
        List<HotCity> hotCities = new ArrayList<>();
        for (Pair<String, String> city : data.getCities()) {
            hotCities.add(new HotCity(city.first, city.second));
        }
        mHotCityAdapter.registerHolder(HotCityHolder.class, hotCities);
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_city_select_header;
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHotCityAdapter = new BaseRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mHotCityAdapter);

        String locationName = mCityModel.locationCityName();
        showLocation(!locationName.equals(Constants.DEFAULT_STR));

    }

    @Override
    public void onLocationComplete(String cityId, boolean success) {
        showLocation(success);
    }

    private void showLocation(boolean locationSuccess) {
        mLocationSucceeded = locationSuccess;
        if (locationSuccess) {
            mTvLocatedCity.setText(mCityModel.locationCityName());
        } else {
            mTvLocatedCity.setText(R.string.located_failed);
        }
    }

    @OnClick(R.id.location_layout)
    void locate() {
        if (mLocationSucceeded) {
            mWeatherModel.updateWeather(mCityModel.getLocationCityId());
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        } else {
            mCityModel.startLocation();
            mTvLocatedCity.setText(R.string.locating);
        }
    }

    static final class HotCity implements BaseAdapterData {
        String mCityName;
        String mCityId;

        HotCity(String cityName, String cityId) {
            mCityName = cityName;
            mCityId = cityId;
        }

        @Override
        public int getItemViewType() {
            return R.layout.item_hot_city;
        }
    }

    public static final class HotCityHolder extends BaseViewHolder<HotCity> {

        @BindView(R.id.tv_hot_city_name)
        TextView mTvHotCityName;
        HotCity mHotCity;

        public HotCityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
            super(itemView, baseRecyclerAdapter);
        }

        @Override
        public void updateItem(HotCity data, int position) {
            mTvHotCityName.setText(data.mCityName);
            mHotCity = data;
        }

        @Override
        public int getContentViewId() {
            return R.layout.item_hot_city;
        }

        @OnClick(R.id.tv_hot_city_name)
        void navigationWeather() {
            ModelManager.getModel(WeatherModel.class).updateWeather(mHotCity.mCityId);
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
    }
}
