package com.silencedut.knowweather;

import android.app.Application;
import android.os.Environment;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.silencedut.knowweather.db.DBManage;
import com.silencedut.knowweather.utils.LogHelper;
import com.silencedut.knowweather.utils.TaskExecutor;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by SilenceDut on 16/10/15.
 */

public class WeatherApplication extends Application {

    private static Application sApplication;
    private static Gson sGson = new Gson();
    private static final String APP_ID = "d29f7bc148";

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/NotoSans-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

        DBManage.getInstance().copyCitysToDB();

        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                PlatformConfig.setWeixin("wxf56994fcbedb2d97", "ceedf892637bed9b8d431e9eb2cf075a");
                PlatformConfig.setQQZone("1105761457", "SdMWEVML7ct0szb1");
                UMShareAPI.get(WeatherApplication.this);

            }
        });

        initCrashReport();

        if (BuildConfig.DEBUG) {

            LogHelper.debugInit();
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
        } else {
            LogHelper.releaseInit();
        }
    }

    private void initCrashReport() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 3 * 1000;
        Beta.largeIconId = R.mipmap.icon;
        Beta.smallIconId = R.mipmap.icon;
        Beta.defaultBannerId = R.mipmap.icon;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Bugly.init(getApplicationContext(), APP_ID, BuildConfig.DEBUG);

    }

    public static Gson getGson() {
        return sGson;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ModelManager.unSubscribeAll();
    }

    public static Application getContext() {
        return sApplication;
    }


}
