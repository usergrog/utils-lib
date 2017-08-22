package com.marssoft.utils.lib.logs;

import com.github.tony19.timber.loggly.LogglyTree;

import timber.log.Timber;

/**
 * Created by Alexey Sidorenko on 07-Jul-16.
 */
public class LogglyLog implements AppLog {

    private String mLogglyToken;

    public LogglyLog(String logglyToken) {
        mLogglyToken = logglyToken;
        Timber.plant(new LogglyTree(mLogglyToken));
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
//      Timber doesn't send verbose messages
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
        Timber.tag(tag);
        Timber.i(msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
        Timber.tag(tag);
        Timber.d(msg);
    }

    @Override
    public void d(String tag, String msg, Throwable ex) {
        Log.d(tag, msg, ex);
        Timber.tag(tag);
        Timber.d(ex, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
        Timber.tag(tag);
        Timber.w(msg);
    }

    @Override
    public void w(String tag, String msg, Throwable ex) {
        Log.w(tag, msg, ex);
        Timber.tag(tag);
        Timber.w(ex, msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
        Timber.tag(tag);
        Timber.e(msg);
    }

    @Override
    public void e(String tag, String msg, Throwable ex) {
        Log.e(tag, msg, ex);
        Timber.tag(tag);
        Timber.e(ex, msg);
    }
}
