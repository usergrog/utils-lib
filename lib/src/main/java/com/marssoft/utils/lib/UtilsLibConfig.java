package com.marssoft.utils.lib;

import android.database.sqlite.SQLiteDatabase;

import com.marssoft.utils.lib.logs.AppLog;

/**
 * Created by Alexey on 22.07.2015.
 */
public class UtilsLibConfig {
    public static SQLiteDatabase sqLiteDatabase;
    public static String parseApiKey;
    public static String parseAppId;
    public static String deviceId;
    public static String deviceName;
    public static int appVersionCode;
    public static boolean writeLogsToDB;
    public static String customLogTag;
    public static AppLog appLogImpl;
    public static String appLogTag;
}
