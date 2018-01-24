package com.silencedut.baselib.commonhelper.log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

class MLog {
    public enum Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR,
    }

    public interface Appender {
        void write(Level level, Object tag, String message);
    }

    /**
     * Log options.
     *
     *
     */
    public static class LogOptions {
        public static final int LEVEL_VERBOSE = 1;
        public static final int LEVEL_DEBUG = 2;
        public static final int LEVEL_INFO = 3;
        public static final int LEVEL_WARN = 4;
        public static final int LEVEL_ERROR = 5;

        /**
         * Uniform tag to be used as log tag; null-ok, if this is null, will use
         * the tag argument in log methods.
         */
        public String uniformTag;

        /**
         * When it is null, all stack traces will be output. Usually this can be
         * set the application package name.
         */
        public String stackTraceFilterKeyword;

        /**
         * The level at which the log method really works(output to DDMS and
         * file).
         *
         * NOTE this setting excludes the file writing of VERBOSE
         * except when set {@link #honorVerbose} to true explicitly.
         * If logLevel is LEVEL_VERBOSE:
         * a) when honorVerbose is true, will output all logs to DDMS and file.
         * b) when honorVerbose is false(default), will output all levels no less
         * than LEVEL_DEBUG to DDMS and file, but for verbose, will only output
         * to DDMS.
         *
         *
         * MUST be one of the LEVEL_* constants.
         */
        public int logLevel = LEVEL_VERBOSE;

        public boolean honorVerbose = false;

        /**
         * Maximum backup log files' size in MB. Can be 0, which means no back
         * up logs(old logs to be discarded).
         */
        public int backUpLogLimitInMB = LogToES.DEFAULT_BAK_FILE_NUM_LIMIT
                * LogToES.MAX_FILE_SIZE;

        /** Default file buffer size. Must be positive. */
        public int buffSizeInBytes = LogToES.DEFAULT_BUFF_SIZE;

        /**
         * Log file name, should not including the directory part. Must be a
         * valid file name(for Android file system).
         */
        public String logFileName = "logs.txt";

        /**
         *  Be used for identify different logs ( which are written by different threads)
         */
        public String logIdentifier = "logs";
    }

    private static volatile LogOptions sOptions = new LogOptions();
    public static String MAIN_THEAD_NAME = "0";

    private static int[] mTimes = new int[4];
    private static String mLogFileNameByHour = "";
    /*private static final ExecutorService sThread = Executors
            .newSingleThreadExecutor();*/

    private static final HandlerThread sHandlerThread;
    private static final Handler sHandler;

    static {
        sHandlerThread = new HandlerThread("LogThread", Process.THREAD_PRIORITY_BACKGROUND);
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper());
    }

    /**
     *
     * @param directory
     *            Where to put the logs folder. Should be a writable directory.
     * @return True for succeeded, false otherwise.
     */
    public static boolean initialize(String directory) {
        return LogToES.setLogPath(directory);
    }

    public static class LogOutputPaths {
        /**
         * The log directory, under which log files are put.
         */
        public String dir;

        /**
         * Current log file absolute file path. NOTE it may be empty.
         *
         */
        public String currentLogFile;

        /**
         * Latest back up file path. null if there is none such file.
         */
        public String latestBackupFile;


    }

    /**
     * Get log output paths.
     * @return null if not ready.
     */
    public static LogOutputPaths getLogOutputPaths() {
        LogOutputPaths ret = new LogOutputPaths();
        if (!getLogOutputPaths(ret)) {

            MLog.error("MLog", "failed to get log output paths.");
        }
        return ret;
    }

    /**
     * Get log output paths.
     * @param out  Output destination.
     * @return True for success, false otherwise.
     */
    public static boolean getLogOutputPaths(LogOutputPaths out) {
        return LogToES.getLogOutputPaths(out, sOptions.logFileName);
    }

    /**
     *
     * @param directory
     *            Where to put the logs folder.
     * @param options
     *            null-ok. Options for log methods.
     * @return True for succeeded, false otherwise.
     */
    public static boolean initialize(String directory, LogOptions options) {
        setOptions(options);
        return LogToES.setLogPath(directory);
    }

    /**
     * Make sure initialize is called before calling this.
     */
    public static void setUniformTag(String tag) {
        if (tag != null && tag.length() != 0) {
            sOptions.uniformTag = tag;
        }
    }

    public static String getLogPath() {
        return LogToES.getLogPath();
    }

    public static LogOptions getOptions() {
        return sOptions;
    }

    private static boolean setOptions(LogOptions options) {
        final LogOptions tmpOp = (options == null ? new LogOptions() : options);
        sOptions = tmpOp;
        sOptions.logIdentifier = sOptions.logFileName.substring(0,sOptions.logFileName.indexOf("."));
        LogToES.setBackupLogLimitInMB(tmpOp.backUpLogLimitInMB);
        LogToES.setBuffSize(tmpOp.buffSizeInBytes);
        return tmpOp.buffSizeInBytes > 0
                && !isNullOrEmpty(tmpOp.logFileName);
    }

    /**
     * Output verbose log. Exception will be caught if input arguments have
     * format error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void verbose(Object obj, String format, Object... args) {
        final boolean shouldOutputVerboseToDDMS = shouldOutputVerboseToDDMS();
        final boolean shouldOutputVerboseToFile = shouldOutputVerboseToFile();
        if (shouldOutputVerboseToDDMS || shouldOutputVerboseToFile) {
            try {
//                StackTraceElement element = getStackTraceElements()[4];
//                int line = getCallerLineNumber(element);
//                String filename = getCallerFilename(element);
                outputVerbose(obj, -1, "", format, getThreadName(),
                        shouldOutputVerboseToDDMS, shouldOutputVerboseToFile, args);
            } catch (java.util.IllegalFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getThreadName(){
        if (Looper.getMainLooper() != Looper.myLooper())
            return String.valueOf(Thread.currentThread().getId());
        return MAIN_THEAD_NAME;
    }

    public static void verboseWithoutLineNumber(Object obj, String format, Object... args) {
        final boolean shouldOutputVerboseToDDMS = shouldOutputVerboseToDDMS();
        final boolean shouldOutputVerboseToFile = shouldOutputVerboseToFile();
        if (shouldOutputVerboseToDDMS || shouldOutputVerboseToFile) {
            try {
                outputVerbose(obj, format,
                        shouldOutputVerboseToDDMS, shouldOutputVerboseToFile, args);
            } catch (java.util.IllegalFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output debug log. This version aims to improve performance by removing
     * the string concatenated costs on release version. Exception will be
     * caught if input arguments have format error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void debug(Object obj, String format, Object... args) {
        if (shouldWriteDebug()) {
//            StackTraceElement element = getStackTraceElements()[4];
//            int line = getCallerLineNumber(element);
//            String filename = getCallerFilename(element);
            outputDebug(obj, format, getThreadName(), -1, "", args);
        }
    }

    public static void debugWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteDebug()) {
            outputDebug(obj, format, args);
        }
    }

    /**
     * Output information log. Exception will be caught if input arguments have
     * format error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void info(Object obj, String format, Object... args) {
        if (shouldWriteInfo()) {
            try {
//                StackTraceElement element = getStackTraceElements()[4];
//                int line = getCallerLineNumber(element);
//                String filename = getCallerFilename(element);
                outputInfo(obj, format, getThreadName(), -1, "", args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void infoWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteInfo()) {
            try {
                outputInfo(obj, format, args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output warning log. Exception will be caught if input arguments have
     * format error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void warn(Object obj, String format, Object... args) {
        if (shouldWriteWarn()) {
            try {
//                StackTraceElement element = getStackTraceElements()[4];
//                int line = getCallerLineNumber(element);
//                String filename = getCallerFilename(element);
                outputWarning(obj, format, getThreadName(), -1, "", args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void warnWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteWarn()) {
            try {
                outputWarning(obj, format, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output error log. Exception will be caught if input arguments have format
     * error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void error(Object obj, String format, Object... args) {
        if (shouldWriteError()) {
            try {
//                StackTraceElement element = getStackTraceElements()[3];
//                int line = getCallerLineNumber(element);
//                String filename = getCallerFilename(element);
                outputError(obj, format, getThreadName(), -1, "", args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void errorWithoutLineNumber(Object obj, String format, Object... args) {
        if (shouldWriteError()) {
            try {
                outputError(obj, format, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output an error log with contents of a Throwable.
     * Exception will be caught if input arguments have format error.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param obj
     * @param format
     *            The format string such as "This is the %d sample : %s".
     * @param t
     *            An Throwable instance.
     * @param args
     *            The args for format.
     *
     *            Reference : boolean : %b. byte, short, int, long, Integer, Long
     *            : %d. NOTE %x for hex. String : %s. Object : %s, for this
     *            occasion, toString of the object will be called, and the
     *            object can be null - no exception for this occasion.
     *
     */
    public static void error(Object obj, String format, Throwable t, Object... args) {
        error(obj, format + '\n' + stackTraceOf(t), args);
    }

    public static void errorWithoutLineNumber(Object obj, String format, Throwable t, Object... args) {
        errorWithoutLineNumber(obj, format + '\n' + stackTraceOf(t), args);
    }

    /**
     * Output an error log with contents of a Throwable.
     *
     * NOTE {@link #initialize(String)} or
     * {@link #initialize(String, LogOptions)} must be called before calling
     * this.
     *
     * @param t
     *            An Throwable instance.
     */
    public static void error(Object obj, Throwable t) {
        if (shouldWriteError()) {
//            StackTraceElement element = getStackTraceElements()[3];
//            int line = getCallerLineNumber(element);
//            String filename = getCallerFilename(element);
//            String methodname = getCallerMethodName(element);
            outputError(obj, t, -1, "", "");
        }
    }

    /*public static void errorWithoutLineNumber(Object obj, Throwable t) {
        if (shouldWriteError()) {
            String methodname = getCallerMethodName();
            outputError(obj, t, methodname);
        }
    }*/

    /**
     * Flush the written logs. The log methods write logs to a buffer.
     *
     * NOTE this will be called if close is called.
     */
    public static void flush() {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                LogToES.flush();
            }
        };

        executeCommand(command);
    }

    /**
     * Close the logging task. Flush will be called here. Failed to call this
     * may cause some logs lost.
     */
    public static void close() {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                if (externalStorageExist()) {
                    LogToES.close();
                }
            }
        };

        executeCommand(command);
    }

    /*public static boolean isOpen() {
        return !sThread.isShutdown() && !sThread.isTerminated() && LogToES.isOpen();
    }*/

    private static void executeCommand(final Runnable command) {
        sHandler.post(command);
    }

    private static String objClassName(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return obj.getClass().getSimpleName();
        }
    }

    private static void writeToLog(final String logText) {
        final long timeMillis = System.currentTimeMillis();
        if (externalStorageExist()) {
            try {
                //按小时分文件
                LogToES.writeLogToFile(LogToES.getLogPath(), createLogFileName(), logText, false, timeMillis);
            } catch (Throwable e) {
                Log.e("MLog", "writeToLog fail !" , e);
            }
        }
    }

    public static String createLogFileName(int year, int month, int day, int hour) {
        StringBuffer buffer = new StringBuffer(getOptions().logIdentifier+"_"+year);
        if(month > 9) {
            buffer.append("_");
            buffer.append(month);
        } else {
            buffer.append("_0");
            buffer.append(month);
        }
        if(day > 9) {
            buffer.append("_");
            buffer.append(day);
        } else {
            buffer.append("_0");
            buffer.append(day);
        }
        if(hour > 9) {
            buffer.append("_");
            buffer.append(hour);
        } else {
            buffer.append("_0");
            buffer.append(hour);
        }
        buffer.append(".txt");
        return buffer.toString();
    }

    private static String createLogFileName() {
        int year, month, day, hour;
        boolean needChangeName = false;
        Calendar c = LogToES.getThreadCalendar();//减少创建对象
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        if(year != mTimes[0]) {
            mTimes[0] = year;
            needChangeName = true;
        }
        if(month != mTimes[1]) {
            mTimes[1] = month;
            needChangeName = true;
        }
        if(day != mTimes[2]) {
            mTimes[2] = day;
            needChangeName = true;
        }
        if(hour != mTimes[3]) {
            mTimes[3] = hour;
            needChangeName = true;
        }
        if(needChangeName) {
            mLogFileNameByHour = createLogFileName(year,month,day,hour);
        }
        return mLogFileNameByHour;
    }

    private static void logToFile(final String logText, final Throwable t) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                StringWriter sw = new StringWriter();
                sw.write(logText);
                sw.write("\n");
                t.printStackTrace(new PrintWriter(sw));
                writeToLog(sw.toString());
            }
        };
        executeCommand(command);
    }

    private static String msgForException(Object obj, String methodname,
                                          String filename, int line) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof String)
            sb.append((String) obj);
        else
            sb.append(obj.getClass().getSimpleName());
        sb.append(" Exception occurs at ");
        sb.append("(P:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");
        sb.append(Thread.currentThread().getId());
        sb.append(") at ");
        sb.append(methodname);
        sb.append(" (");
        sb.append(filename);
        sb.append(":" + line);
        sb.append(")");
        String ret = sb.toString();
        return ret;
    }

    /*private static String msgForTextLog(int logLevel, Object obj, String filename, int line,
                                        String msg) {
        return msgForTextLog(logLevel, obj, filename, line, null, msg);
    }*/

    private static String msgForTextLog(int logLevel, Object obj, String filename, int line,
                                        String thread, String msg) {
        StringBuilder sb = new StringBuilder();

        switch (logLevel) {
            case LogOptions.LEVEL_VERBOSE:
                sb.append("V/: ");
                break;
            case LogOptions.LEVEL_DEBUG:
                sb.append("D/: ");
                break;
            case LogOptions.LEVEL_INFO:
                sb.append("I/: ");
                break;
            case LogOptions.LEVEL_WARN:
                sb.append("W/: ");
                break;
            case LogOptions.LEVEL_ERROR:
                sb.append("E/: ");
                break;
            default:
                sb.append("I/: ");
                break;
        }

        sb.append("[");
        sb.append(objClassName(obj));
        sb.append("]");

        sb.append(msg);
        sb.append("(P:");
        sb.append(Process.myPid());
        sb.append(")");
        sb.append("(T:");

        if (thread != null){
            sb.append(thread);
        }else {
            if (Looper.getMainLooper() == Looper.myLooper())
                sb.append("Main");
            else
                sb.append(Thread.currentThread().getId());
        }
        sb.append(")");

        if (line > 0) {
            sb.append("at (");
            sb.append(filename);
            sb.append(":");
            sb.append(line);
            sb.append(")");
        }
        String ret = sb.toString();
        return ret;
    }

    private static StackTraceElement[] getStackTraceElements(){
        Thread t = Thread.currentThread();
        StackTraceElement[] traceElements = t.getStackTrace();
        return traceElements;
    }

    private static int getCallerLineNumber(StackTraceElement element) {
        try {
            int line = element.getLineNumber();
            return line;
        } catch (Throwable throwable) {
            MLog.error("MLog", "getCallerLineNumber " + throwable);
        }
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack != null && stack.length > 0) {
            return stack[stack.length - 1].getLineNumber();
        }
        return 0;
    }

    private static String getCallerFilename(StackTraceElement element) {
        try {
            String fileName = element.getFileName();
            return fileName;
        } catch (Throwable throwable) {
            MLog.error("MLog", "getCallerFilename " + throwable);
        }
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack != null && stack.length > 0) {
            return stack[stack.length - 1].getFileName();
        }
        return "";
    }

    private static String getCallerMethodName(StackTraceElement element) {
        try {
            return element.getMethodName();
        } catch (Throwable throwable) {
            MLog.error("MLog", "getCallerMethodName " + throwable);
        }
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        if (stack != null && stack.length > 0) {
            return stack[stack.length - 1].getMethodName();
        }
        return "";
    }

    private static String getThreadStacksKeyword() {
        return sOptions.stackTraceFilterKeyword;
    }

    public static void printThreadStacks() {
        printThreadStacks(tagOfStack(), getThreadStacksKeyword(), false, false);
    }

    public static void printThreadStacks(String tag) {
        printThreadStacks(tag, getThreadStacksKeyword(),
                isNullOrEmpty(getThreadStacksKeyword()), false);
    }

    public static void printThreadStacks(Throwable e, String tag) {
        printStackTraces(e.getStackTrace(), tag);
    }

    public static void printThreadStacks(String tag, String keyword) {
        printThreadStacks(tag, keyword, false, false);
    }

    // tag is for output identifier.
    // keyword is for filtering irrelevant logs.
    public static void printThreadStacks(String tag, String keyword,
                                         boolean fullLog, boolean release) {
        printStackTraces(Thread.currentThread().getStackTrace(), tag, keyword,
                fullLog, release);
    }

    public static void printStackTraces(StackTraceElement[] traces, String tag) {
        printStackTraces(traces, tag, getThreadStacksKeyword(),
                isNullOrEmpty(sOptions.stackTraceFilterKeyword), false);
    }

    private static void printStackTraces(StackTraceElement[] traces,
                                         String tag, String keyword, boolean fullLog, boolean release) {
        printLog(tag, "------------------------------------", release);
        for (StackTraceElement e : traces) {
            String info = e.toString();
            if (fullLog
                    || (!isNullOrEmpty(keyword) && info.indexOf(keyword) != -1)) {
                printLog(tag, info, release);
            }
        }
        printLog(tag, "------------------------------------", release);
    }

    private static void printLog(String tag, String log, boolean release) {
        if (release) {
            info(tag, log);
        } else {
            debug(tag, log);
        }
    }

    public static String stackTraceOf(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
        } catch (Throwable throwable) {
            error("stackTraceOf", "" + throwable);
        }
        return sw.toString();
    }

    public static String stackTrace() {
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        return TextUtils.join("\n", traces);
    }

    private static String tag(Object tag) {
        final LogOptions options = sOptions;
        return (options.uniformTag == null ? (tag instanceof String ? (String) tag
                : tag.getClass().getSimpleName())
                : options.uniformTag);
    }

    private static String tagOfStack() {
        return (sOptions.uniformTag == null ? "CallStack" : sOptions.uniformTag);
    }

    private static boolean shouldOutputVerboseToDDMS() {
        return sOptions.logLevel <= LogOptions.LEVEL_VERBOSE;
    }

    private static boolean shouldOutputVerboseToFile() {
        return sOptions.logLevel <= LogOptions.LEVEL_VERBOSE && sOptions.honorVerbose;
    }

    private static boolean shouldWriteDebug() {
        return sOptions.logLevel <= LogOptions.LEVEL_DEBUG;
    }

    private static boolean shouldWriteInfo() {
        return sOptions.logLevel <= LogOptions.LEVEL_INFO;
    }

    private static boolean shouldWriteWarn() {
        return sOptions.logLevel <= LogOptions.LEVEL_WARN;
    }

    private static boolean shouldWriteError() {
        return sOptions.logLevel <= LogOptions.LEVEL_ERROR;
    }

    private static boolean externalStorageExist() {
        return ExternalStorage.getInstance().isExternalStorageAvailable();
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    private static void outputVerbose(final Object obj, final String format, final boolean outToDDMS, final boolean outToFile, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = (args == null || args.length == 0) ? format : String.format(format, args);
                    if (outToDDMS) {
                        Log.v(tag(obj), logText);
                    }
                    if (outToFile) {
                        writeToLog(logText);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputVerbose(final Object obj, final int line,
                                      final String filename, final String format, final String thread, final boolean outToDDMS, final boolean outToFile, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    if (outToDDMS) {
                        String msg = (args == null || args.length == 0) ? format : String.format(format, args);
                        final String logText = msgForTextLog(LogOptions.LEVEL_VERBOSE, obj, filename, line, thread, msg);
                        Log.v(tag(obj), logText.substring(4));
                        if (outToFile) {
                            writeToLog(logText);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputDebug(final Object obj, final String format, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = (args == null || args.length == 0) ? format : String.format(format, args);
                    Log.d(tag(obj), logText);
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputDebug(final Object obj, final String format, final String thread,
                                    final int line, final String filename, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = (args == null || args.length == 0) ? format : String.format(format, args);
                    final String logText = msgForTextLog(LogOptions.LEVEL_DEBUG, obj, filename, line, thread, msg);
                    Log.d(tag(obj), logText.substring(4));
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);

    }

    private static void outputInfo(final Object obj, final String format, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = (args == null || args.length == 0) ? format : String.format(format, args);
                    Log.i(tag(obj), logText);
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputInfo(final Object obj, final String format, final String thread,
                                   final int line, final String filename, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = (args == null || args.length == 0) ? format : String.format(format, args);
                    String logText = msgForTextLog(LogOptions.LEVEL_INFO, obj, filename, line, thread, msg);
                    Log.i(tag(obj), logText.substring(4));
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputWarning(final Object obj, final String format, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = (args == null || args.length == 0) ? format : String.format(format, args);
                    Log.w(tag(obj), logText);
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputWarning(final Object obj, final String format, final String theadName,
                                      final int line, final String filename, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = (args == null || args.length == 0) ? format : String.format(format, args);
                    String logText = msgForTextLog(LogOptions.LEVEL_WARN, obj, filename, line, theadName, msg);
                    Log.w(tag(obj), logText.substring(4));
                    writeToLog(logText);
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputError(final Object obj, final String format, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = (args == null || args.length == 0) ? format : String.format(format, args);
                    // If the last arg is a throwable, print the stack.
                    if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
                        Throwable t = (Throwable) args[args.length - 1];
                        Log.e(tag(obj), logText, t);
                        logToFile(logText, t);
                    } else {
                        Log.e(tag(obj), logText);
                        writeToLog(logText);
                    }
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputError(final Object obj, final String format, final String threadName,
                                    final int line, final String filename, final Object... args) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = (args == null || args.length == 0) ? format : String.format(format, args);
                    String logText = msgForTextLog(LogOptions.LEVEL_ERROR, obj, filename, line, threadName, msg);
                    // If the last arg is a throwable, print the stack.
                    if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
                        Throwable t = (Throwable) args[args.length - 1];
                        Log.e(tag(obj), logText.substring(4), t);
                        logToFile(logText, t);
                    } else {
                        Log.e(tag(obj), logText);
                        writeToLog(logText);
                    }
                } catch (Throwable e) {
                    new Exception("exception when logging \"" + format + "\"", e).printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputError(final Object obj, final Throwable t, final String methodname) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = objClassName(obj);
                    Log.e(tag(obj), logText, t);
                    logToFile(logText, t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        };
        executeCommand(command);
    }

    private static void outputError(final Object obj, final Throwable t,
                                    final int line, final String filename, final String methodname) {
        final Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    String logText = msgForException(obj, methodname, filename, line);
                    Log.e(tag(obj), logText, t);
                    logToFile(logText, t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        };
        executeCommand(command);

    }

}
