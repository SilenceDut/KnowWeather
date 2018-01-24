package com.silencedut.weather_core.api.coreprovider;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.weather_core.corebase.BaseActivity;
import com.silencedut.weather_core.corebase.customview.ShareDialog;

/**
 * Created by SilenceDut on 2018/1/24 .
 */

@HubInject(api = ICoreProvider.class)
public class CoreProviderImpl implements ICoreProvider {
    private static final String TAG = "CoreProviderImpl";
    @Override
    public void onCreate() {

    }


    @Override
    public void showShareDialog(BaseActivity baseActivity,boolean weather) {
        if(baseActivity.isFinishing()) {
            return;
        }
        try {
            new ShareDialog(baseActivity).show(weather);
        }catch (Exception e) {
            LogHelper.info(TAG,"showShareDialog error %s",e);
        }

    }
}
