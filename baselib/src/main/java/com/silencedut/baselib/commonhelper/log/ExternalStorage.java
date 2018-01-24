package com.silencedut.baselib.commonhelper.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;

import java.io.File;


class ExternalStorage {

    private static final String TAG = "ExternalStorage";
    private BroadcastReceiver mExternalStorageReceiver;
    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    private Context mContext;
    private static class SingletonHolder{
        private static final ExternalStorage sInstance = new ExternalStorage();
    }

    //================ public ================
    public static final ExternalStorage getInstance(){
        return SingletonHolder.sInstance;
    }

    /**
     * 如果有外部存储，则返回外部存储路径，没有则使用内部存储的cache路径
     * @param context
     * @param uniqueName
     * @return
     */
    public File getCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                isExternalStorageWriteable() ||
                        !isExternalStorageRemovable() ? getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     *
     * 外部存储可读可写
     * @return
     */
    public boolean isExternalStorageWriteable() {
        return mExternalStorageAvailable && mExternalStorageWriteable;
    }

    /**
     * 外部存储可用（只读）
     * @return
     */
    public boolean isExternalStorageAvailable() {
        return mExternalStorageAvailable;
    }

    /**
     * 初始化，开始监听外部存储的状态
     */
    public void init(Context context) {
        mContext = context;
        updateExternalStorageState();
        startWatchingExternalStorage();
    }

    /**
     * 停止，取消监听器
     */
    public void uninit() {
        if (mContext == null) {
            MLog.error(TAG, "mContext null when stopWatchingExternalStorage");
            return;
        }
        mContext.unregisterReceiver(mExternalStorageReceiver);
    }

    //================ private ================

    private ExternalStorage(){

    }

    private static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    private File getExternalCacheDir() {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }

    /**
     * 更新状态变量
     */
    private void updateExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    /**
     * 注册外部存储监听器
     */
    private void startWatchingExternalStorage() {
        if (mContext == null) {
            MLog.error(TAG, "mContext null when startWatchingExternalStorage");
            return;
        }
        mExternalStorageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            MLog.info(TAG, "Storage: " + intent.getData());
            updateExternalStorageState();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addDataScheme("file");
        mContext.registerReceiver(mExternalStorageReceiver, filter);
        updateExternalStorageState();
    }
}
