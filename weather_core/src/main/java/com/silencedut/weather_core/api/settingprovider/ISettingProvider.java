package com.silencedut.weather_core.api.settingprovider;

import android.content.Context;
import android.support.v4.util.Pair;

import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.corebase.BaseFragment;

/**
 * Created by SilenceDut on 2018/1/22 .
 */

public interface ISettingProvider extends ICoreApi {
    void navigationAboutActivity(Context context);
    Pair<BaseFragment,Integer> provideSettingFragment();
}
