package com.marssoft.utils.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.marssoft.utils.lib.logs.Log;

@SuppressWarnings("UnusedDeclaration")
public class FormatUtil {

    private static final String TAG = FormatUtil.class.getSimpleName();
    public static int DAY_IN_MILLIS = 86400000;
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;

    public static Date IntToDate(int source) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return (Date) df.parseObject(String.valueOf(source));
        } catch (ParseException e) {
            Log.w(TAG, "IntToDate - " + String.valueOf(source), e);
        }
        return null;
    }

    public static String IntToStrDate(int source) {
        StringBuilder result = new StringBuilder();
        try {
            String d = String.valueOf(source);
            result.append(d.substring(6, 8)).append("");
            result.append(d.substring(4, 6)).append("");
            result.append(d.substring(0,4));
        }catch (Exception e){
            Log.e(TAG, "Error converting date", e);
        }
        return result.toString();
    }

    public static String IntToStrDate(String source) {

        return source.substring(6, 8) + "" + source.substring(4, 6) + "" + source.substring(0, 4);
    }

    public static String IntToStrDate(long source) {
        String d = String.valueOf(source);

        return d.substring(6, 8) + "." + d.substring(4, 6) + "." + d.substring(0, 4);
    }

    public static String IntToStrDateTime(long source) {
        // 20120120104709
        // 20.01.2012 10:47
        String d = String.valueOf(source);

        return d.substring(6, 8) + "." + d.substring(4, 6) + "."
                + d.substring(0, 4) + " " + d.substring(8, 10) + ":" + d.substring(10, 12) + ":" + d.substring(12, 14);
    }

    public static int DateToInt(Date source) {
        if (source != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return Integer.parseInt(df.format(source));
        } else {
            Log.w(TAG, "DateToInt(Date source) source is null !!!",null);
        }

        return 19000101;
    }

    public static long DateTimeToInt(Date source) {

        if (source != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            return Long.parseLong(df.format(source));
        } else {
            Log.w(TAG, "DateTimeToInt(Date source) source is null !!!",null);
        }

        return 19000101000000L;
    }

    public static Date IntToDateTime(long source) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return (Date) df.parseObject(String.valueOf(source));
        } catch (ParseException e) {
            printStackTraceToLog(e);
            Log.e(TAG, "IntToDateTime, source = " + source, e);
        }
        Calendar c = new GregorianCalendar(1800, Calendar.JANUARY, 1);
        return c.getTime();
    }

    public static Date IntToDateTime(long source, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return (Date) df.parseObject(String.valueOf(source));
        } catch (ParseException e) {
            printStackTraceToLog(e);
            Log.e(TAG, "IntToDateTime, source = " + source + " format = " + format, e);
        }
        Calendar c = new GregorianCalendar(1800, Calendar.JANUARY, 1);
        return c.getTime();
    }

    private static void printStackTraceToLog(ParseException e) {
        for (StackTraceElement line : e.getStackTrace()) {
            Log.w(TAG, line.toString());
        }
    }

    public static Date StrToDate(String source, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return (Date) df.parseObject(source);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return new Date();
    }

    public static int StrToInt(String source) {
        return Integer.valueOf(
                source.substring(6, 10) + source.substring(3, 5) + source.substring(0, 2));
    }

    public static String DateToStr(Date source, String pattern) {
        if (source != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(source);
        }
        return null;
    }

    public static String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes()); // string.getBytes(Charset.forName("UTF8"))
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, "NoSuchAlgorithmException", e);
        }
        return "";

    }



    public static Calendar Today() {
        Calendar cal = Calendar.getInstance();
        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar addDay(Calendar cal, int days) {
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal;
    }

    public static long UTCToday() {
        return Today().getTimeInMillis();
    }


    public static String formatFloat(float value, String pattern) {
        DecimalFormatSymbols unusualSymbols =
                new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        unusualSymbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat(pattern,unusualSymbols);
        formatter.setGroupingSize(3);
        return formatter.format(value);
    }

    public static String formatDouble(double value, String pattern) {
        DecimalFormatSymbols unusualSymbols =
                new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        unusualSymbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat(pattern,unusualSymbols);
        formatter.setGroupingSize(3);
        return formatter.format(value);
    }


    public static float parseFloat(String value) {
        NumberFormat nf = NumberFormat.getInstance();
        Number n;
        try {
            n = nf.parse(value.trim());
        } catch (ParseException e) {
            try {
                n = nf.parse(value.replace('.',',').trim());
            } catch (ParseException e1) {
                n = 0;
                Log.w(TAG, "Parse error - " + value, e);
            }
        }
        return n.floatValue();
    }

    public static double parseDouble(String value) {
        if (value == null) return 0;
        NumberFormat nf = NumberFormat.getInstance();
        Number n;
        try {
            n = nf.parse(value.trim());
        } catch (ParseException e) {
            try {
                n = nf.parse(value.replace('.',',').trim());
            } catch (ParseException e1) {
                n = 0;
                Log.w(TAG, "Parse error - " + value, e);
            }
        }
        return n.doubleValue();
    }

    public static String bitmapToBase64(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return bitmapToBase64(((BitmapDrawable)drawable).getBitmap());
        }
        return null;
    }

    private static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public static Bitmap bitmapFromBase64(String base64encodedBitmap) {
        if (!TextUtils.isEmpty(base64encodedBitmap)) {
            byte[] imageAsBytes = Base64.decode(base64encodedBitmap.getBytes(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }
        return null;
    }

    public static String digitWithPattern(String string, String pattern) {
        return formatFloat(parseFloat(string), pattern);
    }

    public static String dateToText(Context context, Date date){
        return dateToText(context, Calendar.getInstance().getTime(), date);
    }

    public static String dateToText(Context context, Date date1, Date date2){
        /*
            0–60 mins	 -14m ago
            1–12 hours	-8h ago
            12+ hours and till the beginning of the day	-16:10
            older than 24 hours or yesterday	-5.01.2015 16:10
            future dates	-5.01.2015 16:10
        */

        String result;
        Calendar now = Calendar.getInstance();
        now.setTime(date1);

        Calendar original = Calendar.getInstance();
        original.setTime(date2);
        original.setTimeZone(now.getTimeZone()); // synchronize timezone

        long duration = now.getTime().getTime() - date2.getTime();

        truncateCalendar(now);
        truncateCalendar(original);

        Calendar published2 = Calendar.getInstance();
        published2.setTime(original.getTime());
        published2.add(Calendar.DATE, 1);

        if (duration < 0){
            result = context.getString(R.string.date_time, date2);
        } else if (duration <= HOUR) {
            result = context.getString(R.string.minutes_ago, duration / MINUTE);
        } else if (duration > HOUR &&
                duration <= 12 * HOUR) {
            result =context.getString(R.string.hours_ago, duration / HOUR);
        } else if (duration > 12 * HOUR &&
                original.equals(now)) {
            result = context.getString(R.string.time, date2);
        } else {
            result = context.getString(R.string.date_time, date2);
        }
        return result;
    }

    public static void truncateCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}

