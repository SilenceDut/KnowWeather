package com.silencedut.knowweather.scheduler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;

import com.silencedut.knowweather.scheduler.exception.DefaultErrorBundle;
import com.silencedut.knowweather.scheduler.exception.ErrorBundle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class TaskScheduler {

    private static final AtomicInteger mHandlerCount = new AtomicInteger(1);
    private static ExecutorService mParallelExecutor ;
    private static ExecutorService mTimeOutExecutor ;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Handler sBackgroundHandler ;
    private static boolean sInited;

    private static final int POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 5;

    private static final int TIMEOUT = 30;

    private TaskScheduler() {

    }

    public static void init() {
        if(sInited) {
            return;
        }

        mParallelExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_POOL_SIZE*2),sThreadFactory);
        mTimeOutExecutor = Executors.newCachedThreadPool(sTimeOutThreadFactory);
        HandlerThread handlerThread = new HandlerThread("background",Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        sBackgroundHandler = new Handler(handlerThread.getLooper());
        sInited = true;
    }


    public static Handler provideHandler() {
        String handlerName = "BackgroundHandler #" + mHandlerCount.getAndIncrement();
        return provideHandler(handlerName);
    }

    public static Handler provideHandler(String handlerName) {
        HandlerThread handlerThread = new HandlerThread(handlerName,Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public static void execute(Runnable task) {
        mParallelExecutor.execute(runnableToTask(task));
    }

    public static <R> void execute(Task<R> task) {
        mParallelExecutor.execute(task);
    }

    /**
     * 使用一个单独的线程池来执行超时任务，避免引起他线程不够用导致超时
     ** 通过实现error(Exception) 判断是否为 TimeoutException 来判断是否超时
     * */
    public static <R> void execute(final long timeOutMilliSecond, final Task<R> timeOutTask) {
        final Future future = mTimeOutExecutor.submit(timeOutTask);
        mTimeOutExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get(timeOutMilliSecond,TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e ) {
                    runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            timeOutTask.error(new DefaultErrorBundle(e));
                            timeOutTask.setCanceled(true);
                        }
                    });
                }

            }
        });
    }

    public static Task runnableToTask(final Runnable runnable) {
        return new Task() {
            @Override
            public Object doInBackground() throws Exception {
                runnable.run();
                return null;
            }
        };
    }


    /*
    **  按需实现相应的接口
    **/

    public static abstract class Task<R> implements Runnable {

        public abstract R doInBackground() throws Exception;

        private boolean mCanceled;

        public void setCanceled(boolean canceled) {
            this.mCanceled = canceled;
        }

        public boolean isCanceled() {
            return mCanceled;
        }

        public  void success(R result) {

        }

        public void error(ErrorBundle errorBundle){

        }

        @Override
        public void run() {
            final R result;
            try {
                result = doInBackground();
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!isCanceled()){
                            success(result);
                        }
                    }
                });
            } catch (final Exception e) {
                runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        error(new DefaultErrorBundle(e));
                    }
                });

            }

        }
    }

    /**
     * 在一个特定的后台线程执行任务，可认为是有序的
     * @param runnable
     */
    public static void runOnBackgroundThread(Runnable runnable) {

        sBackgroundHandler.postDelayed(runnable,0);
    }

    public static  void runOnBackgroundThread(Runnable runnable,long delayed) {

        sBackgroundHandler.postDelayed(runnable,delayed);
    }

    public static void removeBackgroundCallback(Runnable runnable) {
        sBackgroundHandler.removeCallbacks(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void removeUICallback(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static ExecutorService getExecutor() {
        return mParallelExecutor;
    }

    public static <R > void notifySuccessToUI(final R response, final TaskCallback.Success<R> successCallback) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                successCallback.onSuccess(response);
            }
        });
    }

    public static <R> void notifySuccessToUI(final R response, final TaskCallback.Callback<R> taskCallback) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                taskCallback.onSuccess(response);
            }
        });
    }

    public static <R> void notifyErrorToUI(final ErrorBundle error, final TaskCallback.Callback<R> taskCallback) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                taskCallback.onError(error);
            }
        });
    }

    private static final ThreadFactory sTimeOutThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r, "werewolf timeoutThread #" + mCount.getAndIncrement());
            thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r, "werewolf thread #" + mCount.getAndIncrement());
            thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };

}
