package com.silencedut.knowweather.weather.callbacks;

/**
 * Created by SilenceDut on 16/10/29.
 */

public interface WeatherCallBack {

    interface NotificationStatus {
        void onAllowNotification(boolean allow);

        void onUpdateNotification();

        void clearAlarm();
    }


    interface LifeAdvice {
        void lifeAdvice(String index, String advice);
    }

}
