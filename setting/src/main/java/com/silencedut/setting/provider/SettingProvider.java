package com.silencedut.setting.provider;

import android.content.Context;
import android.support.v4.util.Pair;

import com.silencedut.hub_annotation.HubInject;
import com.silencedut.setting.R;
import com.silencedut.setting.ui.AboutActivity;
import com.silencedut.setting.ui.SettingFragment;
import com.silencedut.weather_core.api.settingprovider.ISettingProvider;
import com.silencedut.weather_core.corebase.BaseFragment;

/**
 * Created by SilenceDut on 2018/1/22 .
 */
@HubInject(api = ISettingProvider.class)
public class SettingProvider implements ISettingProvider {
    @Override
    public void navigationAboutActivity(Context context) {
        AboutActivity.navigationActivity(context);
    }

    @Override
    public Pair<BaseFragment, Integer> provideSettingFragment() {
        return new Pair<>(SettingFragment.newInstance(), R.drawable.setting_tab_drawable);
    }
}
