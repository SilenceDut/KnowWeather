package com.silencedut.knowweather.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class TimeUtil {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat HOUR_MINUTE = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat HOUR = new SimpleDateFormat("HH");

    private static String AM_TIP = "";
    private static String PM_TIP = "";
    private static String YESTERDAY = "昨天";
    private static String BEFORE_YESTERDAY = "前天";
    public static final long DAY_OF_YEAR = 365;
    public static final long DAY_OF_MONTH = 30;
    public static final long HOUR_OF_DAY = 24;
    public static final long MIN_OF_HOUR = 60;
    public static final long SEC_OF_MIN = 60;
    public static final long MILLIS_OF_SEC = 1000;

    /**
     * 新时间戳显示
     * 0<X<1分钟     ：  刚刚
     * 1分钟<=X<60分钟    :    X分钟前         EX:5分钟前
     * 1小时<=X<24小时    ：  X小时前        EX：3小时前
     * 1天<=X<7天         ：   X天前           Ex： 4天前
     * 1周<=X<1个月     ：   X周前           EX：3周前
     * X>=1个月           :      YY/MM/DD   HH:MM       EX：15/05/15  15:34
     */


    public static long halfAnHourSeconds() {
        return MIN_OF_HOUR * SEC_OF_MIN / 2;
    }

    public static long anHourSeconds() {
        return MIN_OF_HOUR * SEC_OF_MIN;
    }

    public static long twoHourSeconds() {
        return MIN_OF_HOUR * SEC_OF_MIN * 2;
    }

    public static String getTimeTips(String formatTime) {
        String timeTips = formatTime;
        try {
            Date date = DATE_FORMAT.parse(formatTime);
            timeTips = getTimeTips(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeTips;
    }

    public static String getHourMinute() {
        return HOUR_MINUTE.format(new Date());
    }

    private static String getTimeTips(long timeStamp) {
        long now = System.currentTimeMillis() / 1000;
        String tips;
        long diff = now - timeStamp / 1000;
        if (diff < 60) {
            tips = "刚刚";
        } else if ((diff /= 60) < 60) {
            tips = String.format("%d分钟前", diff);
        } else if ((diff /= 60) < 24) {
            tips = String.format("%d小时前", diff);
        } else if ((diff /= 24) < 7) {
            tips = String.format("%d天前", diff);
        } else if ((diff /= 7) < 4) {
            tips = String.format("%d周前", diff);
        } else {

            tips = DATE_FORMAT.format(new Date(timeStamp * 1000));

        }
        return tips;
    }

    public static String milliseconds2String(long milliSeconds) {
        return DATE_FORMAT.format(new Date(milliSeconds));
    }


    public static boolean isToday(long timestamp) {
        long millOfDay = MILLIS_OF_SEC * SEC_OF_MIN * MIN_OF_HOUR * HOUR_OF_DAY;
        return System.currentTimeMillis() / millOfDay == timestamp / millOfDay;
    }

    public static boolean isNight() {
        int currentHour = Integer.parseInt(HOUR.format(new Date()));
        return currentHour >= 19 || currentHour <= 6;
    }
}
