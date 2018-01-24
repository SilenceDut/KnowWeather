package com.silencedut.knowweather.scheduleJob;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import com.silencedut.knowweather.remoteviews.RemoteViewsHelper;
import com.silencedut.router.Router;
import com.silencedut.weather_core.callback.EventCenter;

/**
 * Created by SilenceDut on 2016/10/23 .
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService implements EventCenter.NotificationStatus {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.instance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Router.instance().unregister(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        RemoteViewsHelper.showNotification(this);
        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    @Override
    public void onAllowNotification(boolean allow) {
        if (!allow) {
            RemoteViewsHelper.stopNotification(this);
        } else {
            RemoteViewsHelper.showNotification(this);
        }
    }

    @Override
    public void onUpdateNotification() {
        RemoteViewsHelper.updateNotification(this);
    }

    @Override
    public void clearAlarm() {
        RemoteViewsHelper.stopAlarm(this);
    }

}
