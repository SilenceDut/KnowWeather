package com.silencedut.knowweather.scheduleJob;

import android.content.Context;

/**
 * Created by SilenceDut on 2016/10/23 .
 */

public interface Scheduler {
    void startPolling(Context context, long seconds, Class<?> cls, String action);

    void stopPolling(Context context, Class<?> cls, String action);
}
