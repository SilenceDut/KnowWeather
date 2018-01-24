package com.silencedut.weather_core.appconfig;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.stetho.Stetho;
import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.baselib.commonhelper.persistence.FileHelper;
import com.silencedut.baselib.commonhelper.persistence.PreferencesHelper;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.BuildConfig;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.R;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by SilenceDut on 2018/1/1 .
 */

public class AppConfig {
    private static final String APP_NAME = "knowweather";
    private static final String BUGLY_APPID = "d29f7bc148";

    public static void initialize(Application context) {
        initANRWatch(context);
        initCrashReport(context);
        initFile();
        initLog(context);
        initSharedPreference(context);
        workThreadInit(context);
    }

    private static void workThreadInit(final Context context) {
        TaskScheduler.execute(new Runnable() {
            @Override
            public void run() {
                PlatformConfig.setWeixin("wxf56994fcbedb2d97", "ceedf892637bed9b8d431e9eb2cf075a");
                PlatformConfig.setQQZone("1105761457", "SdMWEVML7ct0szb1");
                UMShareAPI.get(context);
            }
        });
    }


    private static void initCrashReport(Context context) {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 3 * 1000;
        Beta.largeIconId = R.mipmap.core_icon;
        Beta.smallIconId = R.mipmap.core_icon;
        Beta.defaultBannerId = R.mipmap.core_icon;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Bugly.init(context, BUGLY_APPID, BuildConfig.DEBUG);
    }



    private static void initANRWatch(Application context) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context);
            LeakCanary.install(context);
        }
    }

    /**
     * 需要在initFile()后调用
     */

    private static void initLog(Context context) {
        LogHelper.init(context, FileHelper.getLogTrace());
    }


    private static void initFile() {
        FileHelper.init(APP_NAME);
    }


    private static void initSharedPreference(Context context) {
        PreferencesHelper.init(context,APP_NAME);
        PreferencesHelper.setUid(CoreManager.getUid());
    }
}
