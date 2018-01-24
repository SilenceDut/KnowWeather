package com.silencedut.city.ui;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.silencedut.baselib.commonhelper.persistence.PreferencesHelper;
import com.silencedut.city.R;
import com.silencedut.city.R2;
import com.silencedut.city.ui.adapter.AddData;
import com.silencedut.city.ui.adapter.AddHolder;
import com.silencedut.city.ui.adapter.CityWeatherAdapter;
import com.silencedut.city.ui.adapter.FollowedCityData;
import com.silencedut.city.ui.adapter.FollowedCityHolder;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import java.util.List;

import butterknife.BindView;


/**
 * Created by SilenceDut on 16/10/21.
 */

public class CityFragment extends BaseFragment {

    public static final String CITYS_TIPS_SHOW = "CITYS_TIPS_SHOW";
    @BindView(R2.id.city_follow_list)
    RecyclerView mRecyclerView;

    private CityWeatherAdapter mSubscribeCityAdapter;
    private AddData mAddData = new AddData();
    private CityModel mCityModel;
    private boolean mIsVisibleToUser;


    public static BaseFragment newInstance() {
        CityFragment cityFragment;
        cityFragment = new CityFragment();
        return cityFragment;
    }

    @Override
    public void initBeforeView() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.city_fragment_followed;
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setBackgroundResource(R.color.main_background);

        mSubscribeCityAdapter = new CityWeatherAdapter(getContext());
        mSubscribeCityAdapter.registerHolder(FollowedCityHolder.class, R.layout.city_item_followed_city);
        mSubscribeCityAdapter.registerHolder(AddHolder.class, R.layout.city_item_add_city);
        mRecyclerView.setAdapter(mSubscribeCityAdapter);

    }

    @Override
    protected void initDataObserver() {
        mCityModel = ModelProvider.getModel(getActivity(),CityModel.class);
        mCityModel.getFollowedWeather().observe(this, new Observer<List<FollowedCityData>>() {
            @Override
            public void onChanged(@Nullable List<FollowedCityData> followedCityData) {
                onAllFollowedCities(followedCityData);
            }
        });
    }


    public void onAllFollowedCities(List<FollowedCityData> followedCityDatas) {
        mSubscribeCityAdapter.clear();
        mSubscribeCityAdapter.setData(followedCityDatas);
        mSubscribeCityAdapter.addData(mAddData);

        if ((mIsVisibleToUser && !PreferencesHelper.get(CITYS_TIPS_SHOW, false))) {
            mIsVisibleToUser = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.core_AlertDialogStyle);
            builder.setMessage(R.string.city_guide_info);
            builder.setNegativeButton(R.string.core_known, null);
            builder.setPositiveButton(R.string.core_tip_not_show, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesHelper.put(CITYS_TIPS_SHOW, true);
                }
            });
            builder.show();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
    }
}
