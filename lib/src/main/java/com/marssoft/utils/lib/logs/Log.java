package com.marssoft.utils.lib.logs;

import com.marssoft.utils.lib.UtilsLibConfig;

/**
 * Created by Alexey Sidorenko on 07-Jul-16.
 */
public class Log {
    public static void v(String tag, String msg){
        UtilsLibConfig.appLogImpl.v(tag, msg);
    }
    public static void i(String tag, String msg){
        UtilsLibConfig.appLogImpl.i(tag, msg);
    }
    public static void d(String tag, String msg){
        UtilsLibConfig.appLogImpl.d(tag, msg);
    }
    public static void d(String tag, String msg, Throwable ex){
        UtilsLibConfig.appLogImpl.d(tag, msg, ex);
    }
    public static void w(String tag, String msg){
        UtilsLibConfig.appLogImpl.w(tag, msg);
    }
    public static void w(String tag, String msg, Throwable ex){
        UtilsLibConfig.appLogImpl.w(tag, msg, ex);
    }
    public static void e(String tag, String msg){
        UtilsLibConfig.appLogImpl.e(tag, msg);
    }
    public static void e(String tag, String msg, Throwable ex){
        UtilsLibConfig.appLogImpl.e(tag, msg, ex);
    }
}
