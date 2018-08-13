package com.silencedut.weather_core;

import android.app.Application;

import com.silencedut.hub.Hub;
import com.silencedut.hub.IHubActivity;
import com.silencedut.weather_core.api.ICoreApi;
import com.silencedut.weather_core.appconfig.AppConfig;

/**
 * Created by SilenceDut on 2018/1/1 .
 */

public class CoreManager {
    private static Application sContext;

    public static void init(Application context) {
        sContext = context;
        AppConfig.initialize(context);

    }

    public static Application getContext() {
        return sContext;
    }

    public static long getUid() {
        return 0;
    }

    public static  <T extends ICoreApi> T getImpl(Class<T> api) {
        return Hub.getImpl(api);
    }

    public static  <T extends IHubActivity> T getActivityRouter(Class<T> api) {
        return Hub.getActivity(api);
    }

    /**
     * 查询接口的实现类是否存在，除非特殊情况，一般不需要，直接调用{@link #getImpl}就行了
     */
    public static <T extends ICoreApi> boolean implExist(Class<T> api) {
        return Hub.implExist(api);
    }

}
