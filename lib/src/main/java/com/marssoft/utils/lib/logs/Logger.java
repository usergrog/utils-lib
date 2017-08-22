package com.marssoft.utils.lib.logs;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Logger {

    public static final String EXTENSION = ".log";

    public static final String LOG_FILENAME_DATE_FORMAT = "yyyy-MM-dd";

    public static String LOG_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/log";

    public static String PREFIX = "okko:";

    public static String deviceId;

    public static String appVersion;

    public static int LOGLEVEL = 0;

    private static boolean VERBOSE = LOGLEVEL <= 0;

    private static boolean DEBUG = LOGLEVEL <= 1;

    private static boolean INFO = LOGLEVEL <= 2;

    private static boolean WARN = LOGLEVEL <= 3;

    public static void v(String tag, String msg) {
        if (VERBOSE) {
            if (msg != null) {
                Log.v(PREFIX + tag, msg);
                writeFileLog(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (INFO) {
            if (msg != null) {
                Log.i(PREFIX + tag, msg);
                writeFileLog(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            if (msg != null) {
                Log.d(PREFIX + tag, msg);
                writeFileLog(tag, msg);
            }
        }
    }


    public static void w(String tag, String msg) {
        if (WARN) {
            if (msg != null) {
                Log.w(PREFIX + tag, msg);
                writeFileLog(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg, Throwable ex) {
        if (WARN) {
            if (msg != null) {
                Log.w(PREFIX + tag, msg, ex);
                writeFileLog(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (WARN) {
            if (msg != null) {
                Log.e(PREFIX + tag, msg);
                writeFileLog(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg, Throwable ex) {
        if (msg != null) {
            Log.e(PREFIX + tag, msg, ex);
            Log.e(PREFIX + tag, Arrays.toString(ex.getStackTrace()), ex);
            writeFileLog(tag, msg);
            writeFileLog(tag, Log.getStackTraceString(ex));
        }
    }

    private static synchronized void writeFileLog(String tag, String message) {
        String mediaState = Environment.getExternalStorageState();
        if (mediaState.equals(Environment.MEDIA_MOUNTED)
                && !mediaState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            File dir = new File(LOG_FOLDER);
            if (!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(LOG_FILENAME_DATE_FORMAT);
            String logFileName = dateFormat.format(date) + EXTENSION;
            try {
                PrintWriter pw = new PrintWriter(
                        new FileOutputStream(LOG_FOLDER + "/" + logFileName, true));
                pw.println(String.format("%1$Td.%1$Tm.%1$TY %1$tH:%1$tM:%1$tS: - %2$s - %3$s - %4$s - %5$s",
                        new Date(), appVersion, deviceId, tag, message));
                pw.flush();
                pw.close();
            } catch (IOException e) {
                Log.e(PREFIX + tag, "Can't make log file. " + e.getMessage());
            }
        } else {
            Log.v(PREFIX + tag, "media state - " + mediaState);
        }
    }

}

