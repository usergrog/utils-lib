package com.marssoft.utils.lib.logs;

/**
 * Created by Alexey Sidorenko on 07-Jul-16.
 */
public interface AppLog {
    void v(String tag, String msg);
    void i(String tag, String msg);
    void d(String tag, String msg);
    void d(String tag, String msg, Throwable ex);
    void w(String tag, String msg);
    void w(String tag, String msg, Throwable ex);
    void e(String tag, String msg);
    void e(String tag, String msg, Throwable ex);
}
