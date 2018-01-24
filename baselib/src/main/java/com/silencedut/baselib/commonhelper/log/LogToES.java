package com.silencedut.baselib.commonhelper.log;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogToES {
    private static final String BAK_EXT = ".bak";
    private static final String LOG_TIME_FORMAT_STR= "yyyy-MM-dd kk:mm:ss.SSS";

    private static final String BAK_DATE_FORMAT_STR = "-yyyyMMdd-kkmmss.SSS";
    private static String PATTERN_STR = "-[0-9]{8}-[0-9]{6}.[0-9]{3}";
    private static Pattern PATTERN = Pattern.compile(PATTERN_STR);


    private static final FastDateFormat LOG_FORMAT = FastDateFormat
            .getInstance(LOG_TIME_FORMAT_STR);

    private static FastDateFormat simpleDateFormat = FastDateFormat
            .getInstance(BAK_DATE_FORMAT_STR);

    private static ThreadLocal<Calendar> logCalendar = new ThreadLocal(){
        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    };

    /** In MB. */
    public static final int MAX_FILE_SIZE = 100;//修改日志最大文件为100M

    public static final int DEFAULT_BAK_FILE_NUM_LIMIT = 5;//release版 日志备份文件个数
    public static final int DEBUG_BAK_FILE_NUM_LIMIT = 10;//debug版 日志备份文件个数

    /** Back file num limit, when this is exceeded, will delete older logs. */
    private static int mBackFileNumLimit = DEFAULT_BAK_FILE_NUM_LIMIT;

    public static final int DEFAULT_BUFF_SIZE = 32 * 1024;

    /** Buffer size , threshold for flush/close. */
    private static int BUFF_SIZE = DEFAULT_BUFF_SIZE;

    private static Object mLock = new Object();

    /** These two are protected by mLock. */
    private static BufferedWriter mWriter;
    private static String mPath;

    /** To flush by interval. */
    private static long mLastMillis = 0;
    private static final long FLUSH_INTERVAL = 5000;

    private volatile static String mLogPath;

    private static HashMap<String, SimpleDateFormat> mSimpleDateFormatCache = new HashMap<String, SimpleDateFormat>();
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            SimpleDateFormat sdf = mSimpleDateFormatCache.get(format);
            if (sdf == null) {
                sdf = new SimpleDateFormat(format);
                mSimpleDateFormatCache.put(format, sdf);
            }

            return sdf;
        } else {
            return new SimpleDateFormat(format);
        }
    }

    public static void setBackupLogLimitInMB(int logCapacityInMB) {
        if (logCapacityInMB > 0){
            mBackFileNumLimit = logCapacityInMB / MAX_FILE_SIZE;
        }
    }

    public static boolean setLogPath(String logDir) {
        if (logDir == null || logDir.length() == 0) {
            return false;
        }
        mLogPath = logDir;

        new File(logDir).mkdirs();

        return new File(logDir).isDirectory();
    }

    public static String getLogPath() {
        return mLogPath;
    }

    public static void setBuffSize(int bytes) {
        BUFF_SIZE = bytes;
    }

    public static void writeLogToFile(String dir, String fileName, String msg,
                                      boolean immediateClose, long timeMillis) throws IOException {
        writeLog(dir, fileName, msg, immediateClose, timeMillis);
    }

    public static void writeLog(String path, String fileName, String msg,
                                boolean immediateClose, long timeMillis) throws IOException {
        if (path == null || path.length() == 0 || fileName == null || fileName.length() == 0) {
            return;
        }
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        boolean needCreate = false;

        File logFile = new File(dirFile, fileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                /*LogManager.getInstance().addSingleLogRecord(fileName.substring(0, fileName.length() - 4));
                LogManager.getInstance().deleteOldLogs();
                LogManager.getInstance().checkAndCompressLog();;

                *//*SET PATH*//*
                if(LogManager.getInstance().getPathListener() != null && logFile != null){
                    LogManager.getInstance().getPathListener().updateCurrentLogPath(logFile.getAbsolutePath());
                }*/


            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            long fileSize = (logFile.length() >>> 20);// convert to M bytes
            if (fileSize >= MAX_FILE_SIZE) {
                return;
            }
        }

        Calendar logCal = logCalendar.get();
        logCal.setTimeInMillis(timeMillis);
        String strLog = LOG_FORMAT.format(logCal);
//        String strLog = LOG_FORMAT.format(timeMillis);

        StringBuffer sb = new StringBuffer(strLog.length() + msg.length() + 4);
        sb.append(strLog);
        sb.append(' ');
        sb.append(msg);
        sb.append('\n');
        strLog = sb.toString();

        synchronized (mLock) {
            if (mPath == null) {
                mPath = logFile.getAbsolutePath();
                needCreate = true;
            } else if (!equal(mPath, logFile.getAbsolutePath())) {
                BufferedWriter writer = mWriter;
                if (writer != null) {
                    writer.close();
                }
                mWriter = null;
                mPath = null;
                needCreate = true;
            }

            BufferedWriter bufWriter = mWriter;

            if (needCreate || bufWriter == null) {
                mPath = logFile.getAbsolutePath();
                FileWriter fileWriter = new FileWriter(logFile, true);
                bufWriter = new BufferedWriter(fileWriter, BUFF_SIZE);
                mWriter = bufWriter;
            }

            // we can make FileWriter static, but when to close it
            bufWriter.write(strLog);

            // It doesn't matter there are multiple files gets mixed.
            final long curMillis = SystemClock.elapsedRealtime();
            if (curMillis - mLastMillis >= FLUSH_INTERVAL) {
                bufWriter.flush();
                mLastMillis = curMillis;
            }

            if (immediateClose) {
                bufWriter.close();
                mPath = null;
                mWriter = null;
            }
        }
    }

    private static File createFile(String path, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (path.endsWith(File.separator)) {
            sb.append(path).append(fileName);
        }else{
            sb.append(path).append(File.separator).append(fileName);
        }
        return new File(sb.toString());
    }

    private static boolean equal(String s1, String s2) {
        if (s1 != null && s2 != null) {
            return s1.equals(s2);
        } else {
            return s1 == null && s2 == null;
        }
    }


    
    public static boolean getLogOutputPaths(MLog.LogOutputPaths out, String currentName) {
    	String dir = LogToES.getLogPath();
    	if (dir == null || currentName == null) {
    		return false;
    	}
    	out.dir = dir;
    	String current = null;
    	synchronized (mLock) {
			current = mPath;
		}
    	if (current == null) {
    		current = createFile(dir, currentName).getAbsolutePath();
    	}
    	out.currentLogFile = current;
    	
    	// get latest.
    	File folder = new File(dir);
    	File[] files = folder.listFiles();
    	if (files != null) {
    		long maxBackupTime = 0;
            long tempTime;
    		String dest = null;
    		for (File e : files) {
                tempTime = getLogFileBackupTime(e);
    			if (tempTime > maxBackupTime) {
                    maxBackupTime = tempTime;
    				dest = e.getAbsolutePath();
    			}
    		}
    		out.latestBackupFile = dest;
    	}
    	
    	return true;
    }

    /**
     * 获取日志备份创建时间，通过文件名判断，如果是备份文件就解析，解析不到就返回文件最后修改时间
     * @param file file
     * @return 0 or backup millis or lastModified millis
     */
    private static long getLogFileBackupTime(File file) {
        if (file == null || !file.exists() || !isBakFile(file.getAbsolutePath())) {
            return 0;
        }
        long time = 0;
        try {
            String filename = file.getName();
            Matcher matcher = PATTERN.matcher(filename);
            if (matcher.find()) {
                String dateStr = filename.substring(matcher.start(), matcher.end());
                time = getSimpleDateFormat(BAK_DATE_FORMAT_STR).parse(dateStr).getTime();
                Log.i("LogToES", ".bak name:" + dateStr + ", time" + time + ", str:" + simpleDateFormat.format(time));
            } else {
                time = file.lastModified();
                Log.i("LogToES", ".bak find time format wrong, filename:" + filename + ", lastModified:" + time);
            }
        } catch (Throwable throwable) {
            Log.e("LogToES", "getLogFileBackupTime error" + throwable);
            time = file.lastModified();
            Log.i("LogToES", ".bak lastModified:" + time);
        }
        return time;
    }
    
    private static boolean isBakFile(String file) {
    	return file.endsWith(BAK_EXT);
    }

    private static void limitVolume() {
        String dir = getLogPath();
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            return;
        }

        final File files[] = dirFile.listFiles();
        if (files == null || files.length <= Math.max(0, mBackFileNumLimit)) {
            return;
        }

        int numOfDeletable = 0;
        for (int i = 0, N = files.length; i < N; i++) {
            File file = files[i];
            if (isBakFile(file.getName())) {
                ++numOfDeletable;
            }
        }

        if (numOfDeletable <= 0) {
            // really weird, the naming rule have been changed!
            // this function won't work anymore.
            return;
        }

        // the logs.txt and uncaught_exception.txt may be missing,
        // so just allocate same size as the old.
        File[] deletables = new File[numOfDeletable];
        int i = 0;
        for (File e : files) {
            if (i >= numOfDeletable) {
                // unexpected case.
                break;
            }
            
            if (isBakFile(e.getName())) {
                deletables[i++] = e;
            }
        }

        deleteIfOutOfBound(deletables);
    }

    private static void deleteIfOutOfBound(File[] files) {
        if (files.length <= mBackFileNumLimit) {
            return;
        }

        // sort files by create time(time is on the file name) DESC.
        Comparator<? super File> comparator = new Comparator<File>() {

            @Override
            public int compare(File lhs, File rhs) {
                return rhs.getName().compareTo(lhs.getName());
            }

        };

        Arrays.sort(files, comparator);

        final int filesNum = files.length;

        // delete files from index to size.
        for (int i = mBackFileNumLimit; i < filesNum; ++i) {
            File file = files[i];
            if (!file.delete()) {
                // NOTE here we cannot call MLog, we are to be depended by MLog.
                Log.e("LogToES", "LogToES failed to delete file " + file);
            }
        }
    }

    public static void flush() {
        synchronized (mLock) {
            BufferedWriter writer = mWriter;
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void close() {
        synchronized (mLock) {
            BufferedWriter writer = mWriter;
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mPath = null;
        }
    }

    public static boolean isOpen() {
        synchronized (mLock) {
            BufferedWriter writer = mWriter;
            return writer != null;
        }
    }

    public static Calendar getThreadCalendar() {
        return logCalendar.get();
    }

}
