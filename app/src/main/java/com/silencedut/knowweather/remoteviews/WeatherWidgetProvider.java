package com.silencedut.knowweather.remoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.SplashActivity;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.model.WeatherModel;
import com.silencedut.knowweather.weather.entity.WeatherEntity;

/**
 * Created by SilenceDut on 2016/11/10 .
 */

public class WeatherWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_ACTION = "main_activity_update_ui";

    public WeatherWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (UPDATE_ACTION.equals(action)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            updateWidget(context,appWidgetManager);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidget(context,appWidgetManager);
    }
    private void updateWidget(Context context,AppWidgetManager appWidgetManager) {
        WeatherEntity weatherEntity = ModelManager.getModel(WeatherModel.class).getCachedWeather();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        if (weatherEntity == null) {
            return;
        }

        WeatherEntity.BasicEntity basic = weatherEntity.getBasic();
        remoteViews.setTextViewText(R.id.temp,basic.getTemp());
        remoteViews.setTextViewText(R.id.weather_status, basic.getWeather());
        remoteViews.setTextViewText(R.id.city, basic.getCity());
        remoteViews.setImageViewResource(R.id.status_icon, Constants.getIconId(basic.getWeather()));

        Intent newTaskIntent = new Intent(context, SplashActivity.class);
        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, newTaskIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_container, pIntent);
        appWidgetManager.updateAppWidget(new ComponentName(context, WeatherWidgetProvider.class), remoteViews);
    }

}
