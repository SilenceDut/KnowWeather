package com.silencedut.knowweather.citys.ui.presenter;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.citys.adapter.CityInfoData;
import com.silencedut.knowweather.common.BasePresenter;
import com.silencedut.knowweather.model.CityModel;
import com.silencedut.knowweather.scheduler.TaskCallback;

import java.util.List;

/**
 * Created by SilenceDut on 2016/11/15 .
 */

public class SearchPresenter extends BasePresenter<SearchCityView> {
    private CityModel mCityModel;
    private SearchCityView mSearchCityView;

    public SearchPresenter(SearchCityView presenterView) {
        super(presenterView);
        mSearchCityView = presenterView;
        mCityModel = ModelManager.getModel(CityModel.class);
    }

    public void getAllCities() {
        mCityModel.getAllCities(new TaskCallback.Success<List<CityInfoData>>() {

            @Override
            public void onSuccess(List<CityInfoData> response) {
                mSearchCityView.onAllCities(response);
            }
        });
    }

    public void matchCities(final String key) {
        mCityModel.matchCities(key,new TaskCallback.Success<List<CityInfoData>>() {
            @Override
            public void onSuccess(List<CityInfoData> response) {
                mSearchCityView.onMatched(response);
            }
        });
    }
}
