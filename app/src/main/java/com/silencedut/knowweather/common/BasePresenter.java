package com.silencedut.knowweather.common;

import android.content.Context;

import com.silencedut.knowweather.WeatherApplication;
import com.silencedut.router.Router;

/**
 * Created by SilenceDut on 16/10/29.
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mPresentView;
    private Context mContext = WeatherApplication.getContext();

    public Context getContext() {
        return mContext;
    }

    public BasePresenter(T presenterView) {
        attachView(presenterView);
    }

    private void attachView(T presenterView) {
        mPresentView = presenterView;
        Router.instance().register(this);
    }

    public void onDetchView() {
        mPresentView = null;
        Router.instance().unregister(this);
    }


}
