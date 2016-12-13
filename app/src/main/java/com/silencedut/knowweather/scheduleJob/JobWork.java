package com.silencedut.knowweather.scheduleJob;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

/**
 * Created by SilenceDut on 2016/10/23 .
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWork implements Scheduler {

    private static final int JOB_ID = 1;

    @Override
    public void startPolling(Context context, long seconds, Class<?> cls, String action) {

        stopPolling(context, cls, action);

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(context.getPackageName(), cls.getName()));
        builder.setPeriodic(seconds * 1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobScheduler.schedule(builder.build());
    }

    @Override
    public void stopPolling(Context context, Class<?> cls, String action) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);
    }
}
