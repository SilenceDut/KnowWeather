package com.silencedut.knowweather.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;
import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.baselib.commonhelper.utils.Check;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.callbacks.WeatherCallBack;
import com.silencedut.router.Router;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class LifeIndexesHolder extends BaseViewHolder<LifeIndexData> implements WeatherCallBack.LifeAdvice {

    private static final String TAG = "LifeIndexesHolder";

    private static final String[] LIFE_INDEXES= { "舒适度","穿衣", "感冒","运动", "旅游","防晒",  "洗车",  "空气"};
    private static final int[] LIFE_INDEXES_ICONIDS = { R.mipmap.weather_comf,R.mipmap.weather_clothing, R.mipmap.weather_health,R.mipmap.weather_sport_mode, R.mipmap.weather_travel,R.mipmap.weather_protection,  R.mipmap.weather_wash_car,  R.mipmap.weather_air};

    @BindView(R.id.lifeRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.life_advice)
    TextView mLifeAdvice;

    private BaseRecyclerAdapter mLifeAdapter;

    public LifeIndexesHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
        initViews();
    }


    private void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mLifeAdapter = new BaseRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mLifeAdapter);
        mLifeAdapter.registerHolder(LifeItemHolder.class, R.layout.weather_item_life_index);
    }

    @Override
    public void updateItem(LifeIndexData lifeIndexData, int position) {
        List<WeatherData.LifeIndexEntity> lifeIndexesData = lifeIndexData.lifeIndexesData;
        if (Check.isNull(lifeIndexData)) {
            return;
        }
        try {
            List<LifeItemData> lifeItems = new ArrayList<>();
            for (int index = 0; index < lifeIndexesData.size(); index++) {
                lifeIndexesData.get(index).setName(LIFE_INDEXES[index]);
                lifeItems.add(new LifeItemData(lifeIndexesData.get(index), LIFE_INDEXES_ICONIDS[index]));
            }
            mLifeAdapter.setData(lifeItems);
        }catch (Exception e) {
            LogHelper.error(TAG,"updateItem error %s",e);
        }


    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_life;
    }

    @OnClick(R.id.life_advice)
    void onClick() {
        Router.instance().getReceiver(WeatherCallBack.LifeAdvice.class).lifeAdvice(getContext().getString(R.string.weather_lifeIndexes), getContext().getString(R.string.weather_lifeIndexes));
        mLifeAdvice.setVisibility(View.GONE);

    }

    @Override
    public void lifeAdvice(String index, String advice) {
        mLifeAdvice.setText(advice);
        mLifeAdvice.setVisibility(View.VISIBLE);
    }


    private static final class LifeItemData implements BaseAdapterData {

        WeatherData.LifeIndexEntity weatherLifeIndexData;
        int lifeIndexIconId = R.mipmap.weather_sport_mode;

        LifeItemData(WeatherData.LifeIndexEntity weatherLifeIndexData, int lifeIndexIconId) {
            this.weatherLifeIndexData = weatherLifeIndexData;
            this.lifeIndexIconId = lifeIndexIconId;
        }

        WeatherData.LifeIndexEntity getWeatherLifeIndexData() {
            return weatherLifeIndexData;
        }

        @Override
        public int getContentViewId() {
            return R.layout.weather_item_life_index;
        }
    }

    static final class LifeItemHolder extends BaseViewHolder<LifeItemData> {

        @BindView(R.id.life_type)
        TextView lifeType;
        @BindView(R.id.life_level)
        TextView lifeLevel;
        @BindView(R.id.life_index_icon)
        ImageView lifeIndexIcon;
        WeatherData.LifeIndexEntity weatherLifeIndexData;

        public LifeItemHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
            super(itemView, baseRecyclerAdapter);
        }

        @Override
        public void updateItem(LifeItemData lifeItemData, int position) {
            weatherLifeIndexData = lifeItemData.getWeatherLifeIndexData();
            if (Check.isNull(weatherLifeIndexData)) {
                return;
            }
            lifeType.setText(weatherLifeIndexData.getName());
            lifeLevel.setText(weatherLifeIndexData.getLevel());
            lifeIndexIcon.setImageResource(lifeItemData.lifeIndexIconId);
        }

        @Override
        public int getContentViewId() {
            return R.layout.weather_item_life_index;
        }

        @OnClick(R.id.life_index_content)
        public void onClick() {
            Router.instance().getReceiver(WeatherCallBack.LifeAdvice.class).lifeAdvice(weatherLifeIndexData.getName(), weatherLifeIndexData.getContent());
        }
    }

}
