package com.silencedut.knowweather.citys.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.citys.adapter.AddData;
import com.silencedut.knowweather.citys.adapter.AddHolder;
import com.silencedut.knowweather.citys.adapter.CityWeatherAdapter;
import com.silencedut.knowweather.citys.adapter.FollowedCityData;
import com.silencedut.knowweather.citys.adapter.FollowedCityHolder;
import com.silencedut.knowweather.citys.ui.presenter.FollowedCityPresenter;
import com.silencedut.knowweather.citys.ui.presenter.FollowedCityView;
import com.silencedut.knowweather.common.BaseFragment;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.utils.PreferencesUtil;

import java.util.List;

import butterknife.BindView;


/**
 * Created by SilenceDut on 16/10/21.
 */

public class CityFragment extends BaseFragment implements FollowedCityView {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private CityWeatherAdapter mSubscribeCityAdapter;
    private AddData mAddData = new AddData();
    private FollowedCityPresenter mFollowedCityPresenter;
    private boolean mIsVisibleToUser;

    public static CityFragment newInstance() {
        CityFragment cityFragment;
        cityFragment = new CityFragment();
        return cityFragment;
    }

    @Override
    public void initBeforeView() {
        mFollowedCityPresenter = new FollowedCityPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_city;
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setBackgroundResource(R.color.main_background);

        mSubscribeCityAdapter = new CityWeatherAdapter(getContext());
        mSubscribeCityAdapter.registerHolder(FollowedCityHolder.class, R.layout.item_followed_city);
        mSubscribeCityAdapter.registerHolder(AddHolder.class, R.layout.item_add_city);
        mRecyclerView.setAdapter(mSubscribeCityAdapter);

        mFollowedCityPresenter.getFollowedWeather();
    }


    @Override
    public void onAllFollowedCities(List<FollowedCityData> followedCityDatas) {
        mSubscribeCityAdapter.clear();
        mSubscribeCityAdapter.setData(followedCityDatas);
        mSubscribeCityAdapter.addData(mAddData);
    }

    @Override
    public void onFollowedCity(FollowedCityData followedCityData) {
        if (followedCityData == null) {
            //change the default icon position
            mSubscribeCityAdapter.notifyDataSetChanged();
            return;
        }

        mSubscribeCityAdapter.getData().remove(mAddData);
        mSubscribeCityAdapter.addData(followedCityData);
        mSubscribeCityAdapter.addData(mAddData);

        if (!mIsVisibleToUser) {
            return;
        }

        if (!PreferencesUtil.get(Constants.CITYS_TIPS_SHOW, false) && mFollowedCityPresenter.followedCitiesNumber() != 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
            builder.setMessage(R.string.city_guide_info);
            builder.setNegativeButton(R.string.known, null);
            builder.setPositiveButton(R.string.tip_not_show, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesUtil.put(Constants.CITYS_TIPS_SHOW, true);
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
