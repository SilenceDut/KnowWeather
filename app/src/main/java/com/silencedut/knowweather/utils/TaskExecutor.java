package com.silencedut.knowweather.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import java.util.concurrent.Executor;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class TaskExecutor {

    private static Executor mParallelExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Handler sIoHandler ;
    private static boolean sInited;

    public static void init() {
        if(sInited) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread("ioThread",Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        sIoHandler = new Handler(handlerThread.getLooper());
        sInited = true;
    }

    private TaskExecutor() {

    }

    public static void runOnIoThread(Runnable runnable) {
        runOnIoThread(runnable,0);
    }

    public static  void runOnIoThread(Runnable runnable,long delayed) {

        sIoHandler.postDelayed(runnable,delayed);
    }

    public static void removeIoCallback(Runnable runnable) {
        sIoHandler.removeCallbacks(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void removeUICallback(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void executeTask(BackgroundTask task) {
        mParallelExecutor.execute(task);
    }

    public static abstract class BackgroundTask implements Runnable {
        public BackgroundTask () {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }
    }
}
