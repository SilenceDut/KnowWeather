package com.silencedut.knowweather.scheduleJob;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.silencedut.baselib.commonhelper.persistence.PreferencesHelper;
import com.silencedut.weather_core.Version;
import com.silencedut.weather_core.corebase.ResourceProvider;

/**
 * Created by SilenceDut on 2016/10/23 .
 */

public class PollingUtils {

    public static void startService(Context context, boolean allowPoll) {

        Class cls = Version.buildVersion() > Build.VERSION_CODES.LOLLIPOP ? JobSchedulerService.class : AlarmService.class;
        context.startService(new Intent(context, cls));
        if (!allowPoll) {
            return;
        }
        startPolling(context);
    }

    //开启轮询
    private static void startPolling(Context context) {
        Scheduler scheduler;
        long seconds = ResourceProvider.getSchedule(PreferencesHelper.get(ResourceProvider.POLLING_TIME, 0));
        if (Version.buildVersion() > Build.VERSION_CODES.LOLLIPOP) {
            scheduler = new JobWork();
            scheduler.startPolling(context, seconds, JobSchedulerService.class, JobSchedulerService.class.getSimpleName());
        } else {
            scheduler = new AlarmWork();
            scheduler.startPolling(context, seconds, AlarmService.class, AlarmService.class.getSimpleName());
        }
    }

    //停止轮询
    public static void stopPolling(Context context) {
        Scheduler scheduler;
        if (Version.buildVersion() > Build.VERSION_CODES.LOLLIPOP) {
            scheduler = new JobWork();
            scheduler.stopPolling(context, JobSchedulerService.class, JobSchedulerService.class.getSimpleName());
        } else {
            scheduler = new AlarmWork();
            scheduler.stopPolling(context, AlarmService.class, AlarmService.class.getSimpleName());
        }
    }
}
