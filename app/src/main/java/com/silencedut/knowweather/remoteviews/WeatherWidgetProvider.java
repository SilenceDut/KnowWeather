package com.silencedut.knowweather.remoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.SplashActivity;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.ResourceProvider;
import com.silencedut.weather_core.corebase.StatusDataResource;

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


        WeatherData weatherData = null;
        LiveData<StatusDataResource<WeatherData>> liveWeatherData = CoreManager.getImpl(IWeatherProvider.class).getWeatherData();
        if(liveWeatherData.getValue() !=null && liveWeatherData.getValue().isSucceed()) {
             weatherData = liveWeatherData.getValue().data;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_widget_layout);

        if (weatherData == null) {
            return;
        }

        WeatherData.BasicEntity basic = weatherData.getBasic();
        remoteViews.setTextViewText(R.id.temp,basic.getTemp());
        remoteViews.setTextViewText(R.id.weather_status, basic.getWeather());
        remoteViews.setTextViewText(R.id.city, basic.getCity());
        remoteViews.setImageViewResource(R.id.status_icon, ResourceProvider.getIconId(basic.getWeather()));

        Intent newTaskIntent = new Intent(context, SplashActivity.class);
        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, newTaskIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_container, pIntent);
        appWidgetManager.updateAppWidget(new ComponentName(context, WeatherWidgetProvider.class), remoteViews);
    }

}
