package com.silencedut.knowweather.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

/**
 * Created by SilenceDut on 16/10/27 .
 */

public class LogHelper {

    public static void debugInit() {
        Logger.init("debug").logLevel(LogLevel.FULL);
    }

    public static void releaseInit() {
        Logger.init("release").logLevel(LogLevel.NONE);
    }

    public static Printer tag(String tag) {
        return Logger.t(tag);
    }

    public static void d(String log, Object... args) {
        Logger.d(log, args);
    }

    public static void i(String log, Object... args) {
        Logger.i(log, args);
    }

    public static void v(String log, Object... args) {
        Logger.v(log, args);
    }

    public static void w(String log, Object... args) {
        Logger.w(log, args);
    }

    public static void e(String log, Object... args) {
        Logger.e(log, args);
    }


    public static void e(Throwable throwable, String log, Object... args) {
        Logger.e(throwable, log, args);
    }
}
