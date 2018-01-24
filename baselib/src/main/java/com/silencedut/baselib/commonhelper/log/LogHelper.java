package com.silencedut.baselib.commonhelper.log;

import android.content.Context;

import com.silencedut.baselib.BuildConfig;

/**
 * Created by SilenceDut on 2018/1/1 .
 */

public class LogHelper  {
    private static final String TAG = "LogHelper";
    public static void init(Context context , String logDir) {
        try {
            //开始监听外部存储的状态
            ExternalStorage.getInstance().init(context);

            MLog.LogOptions options = new MLog.LogOptions();
            if (BuildConfig.DEBUG){
                options.logLevel = MLog.LogOptions.LEVEL_VERBOSE;
            } else {
                options.logLevel = MLog.LogOptions.LEVEL_INFO;
            }
            options.honorVerbose = false;
            options.logFileName = "logs.txt";
            MLog.initialize(logDir, options);
            MLog.info(TAG, "init MLog, logDir: %s fileName: %s level: %d", logDir, options.logFileName,
                    options.logLevel);
        }catch (Throwable t){
            t.printStackTrace();
        }
    }


    public static void info(Object tag, String format, Object... args) {
        MLog.info(tag,format,args);
    }

    public static void error(Object tag, String format, Object... args) {
        MLog.error(tag,format,args);
    }
}
