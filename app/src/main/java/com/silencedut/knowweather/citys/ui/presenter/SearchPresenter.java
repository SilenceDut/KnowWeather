package com.silencedut.knowweather.citys.ui.presenter;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.common.BasePresenter;
import com.silencedut.knowweather.model.CityModel;

/**
 * Created by SilenceDut on 2016/11/15 .
 */

public class SearchPresenter extends BasePresenter<SearchCityView> {
    private CityModel mCityModel;

    public SearchPresenter(SearchCityView presenterView) {
        super(presenterView);
        mCityModel = ModelManager.getModel(CityModel.class);
    }

    public void getAllCities() {
        mCityModel.getAllCities();
    }

    public void matchCities(final String key) {
        mCityModel.matchCities(key);
    }
}
