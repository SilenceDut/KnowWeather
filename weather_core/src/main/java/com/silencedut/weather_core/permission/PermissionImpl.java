package com.silencedut.weather_core.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.hub_annotation.HubInject;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.corebase.BaseActivity;
import com.silencedut.weather_core.location.ILocationApi;

/**
 * Created by SilenceDut on 2018/1/17 .
 */

@HubInject(api = IPermissionApi.class)
public class PermissionImpl implements IPermissionApi {
    private static final String TAG = "PermissionImpl";
    private static final int URGENT_PERMISSION = 0x01;

    @Override
    public void onCreate() {

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void initUrgentPermission(final BaseActivity activity) {

        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(activity,permissions, URGENT_PERMISSION);

        for(final String permission : permissions) {

            if((ActivityCompat.checkSelfPermission(CoreManager.getContext(), permission) != PackageManager.PERMISSION_GRANTED)){
                if(Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                    CoreManager.getImpl(ILocationApi.class).startLocation();
                }else if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)){
                    CoreManager.getImpl(ICityProvider.class).loadCitys();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(BaseActivity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(activity.isFinishing() || activity.isDestroyed()) {
           return;
        }

        LogHelper.info(TAG,"onRequestPermissionsResult %s,%s,%s",requestCode,permissions.length,grantResults.length);

        if (requestCode == URGENT_PERMISSION ) {

            for (int index = 0; index < permissions.length;index ++ ) {

                if(Manifest.permission.ACCESS_FINE_LOCATION .equals(permissions[index]) && grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    CoreManager.getImpl(ILocationApi.class).startLocation();
                }else if(Manifest.permission.WRITE_EXTERNAL_STORAGE .equals(permissions[index]) && grantResults[index] == PackageManager.PERMISSION_GRANTED){
                    CoreManager.getImpl(ICityProvider.class).loadCitys();
                }
            }
        }
    }


}
