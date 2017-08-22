package com.marssoft.utils.lib.logs;

import android.text.TextUtils;

import com.marssoft.utils.lib.DeviceUtils;
import com.marssoft.utils.lib.UtilsLibConfig;

/**
 * Created by Alexey Sidorenko on 07-Jul-16.
 */
public class AppLogImpl implements AppLog {

    int mLogLevel = 0;

    public AppLogImpl() {
        String logLevel = DeviceUtils.getSystemProperty(UtilsLibConfig.appLogTag);
        if (TextUtils.isEmpty(logLevel)) {
            logLevel = "WARN";
        }
        //ERROR, WARN, INFO, DEBUG, VERBOSE
        switch (logLevel) {
            case "VERBOSE":
                mLogLevel = 0;
                break;
            case "INFO":
                mLogLevel = 1;
                break;
            case "DEBUG":
                mLogLevel = 2;
                break;
            case "WARN":
                mLogLevel = 3;
                break;
            case "ERROR":
                mLogLevel = 4;
                break;
            default:
                mLogLevel = 0;
        }
    }

    public int getLogLevel() {
        return mLogLevel;
    }

    public void setLogLevel(int logLevel) {
        mLogLevel = logLevel;
    }

    @Override
    public void v(String tag, String msg) {
        if (mLogLevel == 0){
            android.util.Log.v(tag, msg);
        }
    }

    @Override
    public void i(String tag, String msg) {
        if (mLogLevel <= 1) {
            android.util.Log.i(tag, msg);
        }
    }

    @Override
    public void d(String tag, String msg) {
        if (mLogLevel <= 2) {
            android.util.Log.d(tag, msg);
        }
    }

    @Override
    public void d(String tag, String msg, Throwable ex) {
        if (mLogLevel <= 3) {
            android.util.Log.d(tag, msg, ex);
        }
    }

    @Override
    public void w(String tag, String msg) {
        if (mLogLevel <= 3) {
            android.util.Log.w(tag, msg);
        }
    }

    @Override
    public void w(String tag, String msg, Throwable ex) {
        if (mLogLevel <= 3) {
            android.util.Log.w(tag, msg, ex);
        }
    }

    @Override
    public void e(String tag, String msg) {
        if (mLogLevel <= 4) {
            android.util.Log.e(tag, msg);
        }
    }

    @Override
    public void e(String tag, String msg, Throwable ex) {
        if (mLogLevel <= 4) {
            android.util.Log.e(tag, msg, ex);
        }
    }
}
