package com.silencedut.weather_core.api.coreprovider;

import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.corebase.BaseActivity;

/**
 * Created by SilenceDut on 2018/1/24 .
 */

public interface ICoreProvider extends ICoreApi {
    void showShareDialog(BaseActivity baseActivity,boolean weather);
}
