package com.silencedut.knowweather.weather.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseAdapterData;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.weather.callbacks.WeatherCallBack;
import com.silencedut.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class LifeIndexesHolder extends BaseViewHolder<LifeIndexData> implements WeatherCallBack.LifeAdvice {

    private static final int[] LIFE_INDEXES_ICONIDS = {R.mipmap.protection, R.mipmap.clothing, R.mipmap.sport_mode, R.mipmap.shopping, R.mipmap.sun_cure, R.mipmap.wash_car, R.mipmap.health, R.mipmap.night_light};

    @BindView(R.id.lifeRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.life_advice)
    TextView mLifeAdvice;

    private BaseRecyclerAdapter mLifeAdapter;

    public LifeIndexesHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        Router.instance().register(this);
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mLifeAdapter = new BaseRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mLifeAdapter);
        mLifeAdapter.registerHolder(LifeItemHolder.class, R.layout.item_life_index);
    }

    @Override
    public void updateItem(LifeIndexData lifeIndexData, int position) {
        List<WeatherEntity.LifeIndexEntity> lifeIndexesData = lifeIndexData.lifeIndexesData;
        if (Check.isNull(lifeIndexData)) {
            return;
        }
        List<LifeItemData> lifeItems = new ArrayList<>();
        for (int index = 0; index < lifeIndexesData.size(); index++) {
            lifeItems.add(new LifeItemData(lifeIndexesData.get(index), LIFE_INDEXES_ICONIDS[index]));
        }
        mLifeAdapter.setData(lifeItems);

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_life;
    }

    @OnClick(R.id.life_advice)
    public void onClick() {
        Router.instance().getReceiver(WeatherCallBack.LifeAdvice.class).lifeAdvice(getContext().getString(R.string.lifeIndexes), getContext().getString(R.string.lifeIndexes));
        mLifeAdvice.setVisibility(View.GONE);

    }

    @Override
    public void lifeAdvice(String index, String advice) {
        mLifeAdvice.setText(advice);
        mLifeAdvice.setVisibility(View.VISIBLE);
    }


    private static final class LifeItemData implements BaseAdapterData {

        WeatherEntity.LifeIndexEntity weatherLifeIndexData;
        int lifeIndexIconId = R.mipmap.sport_mode;

        LifeItemData(WeatherEntity.LifeIndexEntity weatherLifeIndexData, int lifeIndexIconId) {
            this.weatherLifeIndexData = weatherLifeIndexData;
            this.lifeIndexIconId = lifeIndexIconId;
        }

        WeatherEntity.LifeIndexEntity getWeatherLifeIndexData() {
            return weatherLifeIndexData;
        }

        @Override
        public int getItemViewType() {
            return R.layout.item_life_index;
        }
    }

    static final class LifeItemHolder extends BaseViewHolder<LifeItemData> {

        @BindView(R.id.life_type)
        TextView lifeType;
        @BindView(R.id.life_level)
        TextView lifeLevel;
        @BindView(R.id.life_index_icon)
        ImageView lifeIndexIcon;
        WeatherEntity.LifeIndexEntity weatherLifeIndexData;

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
            return R.layout.item_life_index;
        }

        @OnClick(R.id.life_index_content)
        public void onClick() {
            Router.instance().getReceiver(WeatherCallBack.LifeAdvice.class).lifeAdvice(weatherLifeIndexData.getName(), weatherLifeIndexData.getContent());
        }
    }

}
