package com.silencedut.weather_core.callback;

/**
 * Created by SilenceDut on 16/10/29.
 */

public interface EventCenter {

    interface NotificationStatus {
        void onAllowNotification(boolean allow);

        void onUpdateNotification();

        void clearAlarm();
    }

}
