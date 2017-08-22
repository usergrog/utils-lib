package com.marssoft.utils.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * It stores all settings and preferences for application and user
 */
public class DeviceUtils {

    /**
     * Gets devices id (IMEI), if we couldn't get device id, we will generate it based on device
     * components
     *
     * @return IMEI or generated 13-digit id
     */
    public static String getDeviceId(Context context) {
        String uid = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            uid = Build.SERIAL;
        } else try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            uid = (String) get.invoke(c, "ro.serialno");
        } catch (Exception ignored) {
        }
        if (TextUtils.isEmpty(uid)
                || uid.equals("unknown")) {

            if (Build.VERSION.SDK_INT < 23) {
                uid = Build.SERIAL;
                TelephonyManager tManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                uid = tManager.getDeviceId();
            } else {
                uid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            if (TextUtils.isEmpty(uid)) {
                uid = "35" + //we make this look like a valid IMEI
                        Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                        Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                        Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                        Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                        Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                        Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                        Build.USER.length() % 10; //13 digits;
            }
        }
        return uid;
    }

    public static String getAppVersion(Context context) {
        String version = "";
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getDeviceImei(Context context) {
        String uid = null;
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        uid = tManager.getDeviceId();
        if (TextUtils.isEmpty(uid)) {
            uid = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits;
        }
        return uid;
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public static String getSystemProperty(String key) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            //noinspection unchecked
            Method method = clazz.getDeclaredMethod("get", String.class);
            return (String) method.invoke(null, key);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}