package com.silencedut.knowweather.remoteviews;

/**
 * Created by SilenceDut on 2016/10/29 .
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.silencedut.knowweather.MainActivity;
import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.model.WeatherModel;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.utils.TimeUtil;
import com.silencedut.knowweather.utils.Version;
import com.silencedut.knowweather.weather.entity.WeatherEntity;


public class RemoteViewsHelper {

    private static final int NOTICE_ID_TYPE_0 = R.string.app_name;
    private static final int NOTICE_ID_TYPE_ALARM = 0x0001;


    public static void showNotification(Service service) {
        boolean show = PreferencesUtil.get(Constants.NOTIFICATION_ALLOW, true);
        if (!show) {
            return;
        }

        Notification notification = RemoteViewsHelper.generateCustomNotification(service);
        service.startForeground(NOTICE_ID_TYPE_0, notification);// 开始前台服务
    }

    public static void updateNotification(Service service) {
        boolean show = PreferencesUtil.get(Constants.NOTIFICATION_ALLOW, true);
        if (!show||service==null) {
            return;
        }

        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = RemoteViewsHelper.generateCustomNotification(service);
        notificationManager.notify(NOTICE_ID_TYPE_0, notification);
    }

    public static void stopNotification(Service service) {

        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTICE_ID_TYPE_0);
        notificationManager.cancel(NOTICE_ID_TYPE_ALARM);
        service.stopForeground(true);
    }

    public static void stopAlarm(Service service) {
        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTICE_ID_TYPE_ALARM);
    }

    @TargetApi(16)
    public static void generateAlarmNotification(Context context) {

        if (!(PreferencesUtil.get(Constants.ALARM_ALLOW, true) && PreferencesUtil.get(Constants.NOTIFICATION_ALLOW, true))) {
            return;
        }

        WeatherEntity weatherEntity = ModelManager.getModel(WeatherModel.class).getCachedWeather();


        if (weatherEntity == null || weatherEntity.getAlarms() == null || weatherEntity.getAlarms().size() == 0) {
            return;
        }

        WeatherEntity.AlarmsEntity alarmsEntity = weatherEntity.getAlarms().get(0);

        Notification notification = new NotificationCompat.Builder(context).setOngoing(false).setPriority(NotificationCompat.PRIORITY_MAX).setContentTitle(alarmsEntity.getAlarmTypeDesc()).setContentText(alarmsEntity.getAlarmDesc()).setSmallIcon(R.mipmap.icon).build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_ALARM, notification);

    }

    @TargetApi(16)
    private static Notification generateCustomNotification(Context context) {

        NotificationCompat.Builder
                builder =  new NotificationCompat
                .Builder(context)
                .setContent(getNotificationContentView(context))
                .setPriority(NotificationCompat.PRIORITY_MAX).setOngoing(true);

        if (Version.buildVersion() >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.small_icon);
        } else {
            builder.setSmallIcon(R.mipmap.icon);
        }


        Notification notification = builder.build();
        //wrap_content fit
        if (Version.buildVersion() >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = getNotificationContentView(context);
        }

        generateAlarmNotification(context);

        return notification;
    }

    private static RemoteViews getNotificationContentView(Context context) {
        WeatherEntity weatherEntity = ModelManager.getModel(WeatherModel.class).getCachedWeather();
        int themeId = Constants.getNotificationThemeId(PreferencesUtil.get(Constants.NOTIFICATION_THEME, 1));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), themeId);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_container, contentIntent);

        if (weatherEntity == null) {
            return remoteViews;
        }

        WeatherEntity.BasicEntity basic = weatherEntity.getBasic();
        remoteViews.setTextViewText(R.id.weather_temp, basic.getTemp());
        remoteViews.setTextViewText(R.id.weather_status, basic.getWeather());
        remoteViews.setTextViewText(R.id.city, basic.getCity());
        remoteViews.setTextViewText(R.id.post_time, TimeUtil.getHourMinute() + " 更新");
        remoteViews.setImageViewResource(R.id.weather_icon, Constants.getIconId(basic.getWeather()));


        Intent updateIntent = new Intent(WeatherWidgetProvider.UPDATE_ACTION);
        context.sendBroadcast(updateIntent);

        return remoteViews;
    }

}
