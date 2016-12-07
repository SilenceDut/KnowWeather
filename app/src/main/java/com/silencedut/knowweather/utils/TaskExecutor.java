package com.silencedut.knowweather.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class TaskExecutor {

    private static Executor mParallelExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
    private static Executor mSerialExecutor = AsyncTask.SERIAL_EXECUTOR;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private TaskExecutor() {
        mParallelExecutor = THREAD_POOL_EXECUTOR;
        mSerialExecutor = AsyncTask.SERIAL_EXECUTOR;
    }

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void executeTask(Runnable task) {
        executeTask(task, true);
    }

    public static void executeTaskSerially(Runnable task) {
        executeTask(task, false);
    }

    public static void executeTask(Runnable task, boolean parallel) {
        if (parallel) {
            mParallelExecutor.execute(task);
            return;
        }
        mSerialExecutor.execute(task);
    }
}
