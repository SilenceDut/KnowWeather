package com.silencedut.weather_core.permission;

import android.support.annotation.NonNull;

import com.silencedut.weather_core.api.BaseCoreApi;
import com.silencedut.weather_core.corebase.BaseActivity;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

public interface IPermissionApi extends BaseCoreApi {
    void initUrgentPermission(BaseActivity activity);
    void onRequestPermissionsResult(BaseActivity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
