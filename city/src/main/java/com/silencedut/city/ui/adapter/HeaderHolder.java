package com.silencedut.city.ui.adapter;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;
import com.silencedut.baselib.commonhelper.utils.Check;
import com.silencedut.city.R;
import com.silencedut.city.R2;
import com.silencedut.router.Router;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.City;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.location.ILocationApi;
import com.silencedut.weather_core.location.LocationNotification;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HeaderHolder extends BaseViewHolder<HeaderData> implements LocationNotification{

    @BindView(R2.id.tv_located_city)
    TextView mTvLocatedCity;
    @BindView(R2.id.city_header_recyclerView)
    RecyclerView mRecyclerView;
    private boolean mLocationSucceeded;

    private BaseRecyclerAdapter mHotCityAdapter;


    public HeaderHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
        initViews();

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
        return R.layout.city_item_city_select_header;
    }


    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHotCityAdapter = new BaseRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mHotCityAdapter);

        City locatedCity = CoreManager.getImpl(ILocationApi.class).getLocatedCity();
        showLocation(locatedCity!=null);

    }


    private void showLocation(boolean locationSuccess) {
        mLocationSucceeded = locationSuccess;
        if (locationSuccess) {
            mTvLocatedCity.setText(CoreManager.getImpl(ILocationApi.class).getLocatedCity().country);
        } else {
            mTvLocatedCity.setText(R.string.city_located_failed);
        }
    }

    @OnClick(R2.id.location_layout)
    void locate() {
        if (mLocationSucceeded) {
            CoreManager.getImpl(IWeatherProvider.class).updateWeather(CoreManager.getImpl(ILocationApi.class).getLocatedCityId());
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        } else {
            CoreManager.getImpl(ILocationApi.class).startLocation();
            mTvLocatedCity.setText(R.string.city_locating);
        }
    }

    @Override
    public void onLocation(boolean success, String cityId) {
        showLocation(success);
    }

    static final class HotCity implements BaseAdapterData {
        String mCityName;
        String mCityId;

        HotCity(String cityName, String cityId) {
            mCityName = cityName;
            mCityId = cityId;
        }

        @Override
        public int getContentViewId() {
            return R.layout.city_item_hot_city;
        }
    }

    public static final class HotCityHolder extends BaseViewHolder<HotCity> {

        @BindView(R2.id.tv_hot_city_name)
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
            return R.layout.city_item_hot_city;
        }

        @OnClick(R2.id.tv_hot_city_name)
        void navigationWeather() {
            CoreManager.getImpl(IWeatherProvider.class).updateWeather(mHotCity.mCityId);
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
    }
}
