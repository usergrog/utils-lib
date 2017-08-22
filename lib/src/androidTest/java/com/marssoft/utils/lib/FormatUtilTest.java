package com.marssoft.utils.lib;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import java.util.Calendar;
import java.util.Date;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by alexey on 12-Jan-16.
 */
@RunWith(AndroidJUnit4.class)
public class FormatUtilTest extends InstrumentationTestCase {
    Context mContext;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mContext = getTargetContext();
    }

    @Test
    public void testDateToText() throws Exception {
       /*
            +10 mins	 ???
            0–60 mins	 -14m ago
            1–12 hours	-8h ago
            12+ hours and till the beginning of the day	-16:10
            older than 24 hours or yesterday	-5.01.2015 16:10
            future dates	-5.01.2015 16:10
        */
        // init
        Date now = FormatUtil.StrToDate("2016-11-30T12:14:59.980+0200", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date test = FormatUtil.StrToDate("2016-11-30T13:14:59.980+0300", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(now);
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(test);


        FormatUtil.truncateCalendar(nowDate);
        FormatUtil.truncateCalendar(testDate);
        nowDate.add(Calendar.HOUR_OF_DAY, 14);
        testDate.add(Calendar.HOUR_OF_DAY, 14);

        testDate.add(Calendar.MINUTE, +15);
        String result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is(String.format("%1$Td.%1$Tm.%1$TY %1$TH:%1$TM",testDate)));

        testDate.add(Calendar.MINUTE, -30);
        result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is("15m ago"));

        testDate.add(Calendar.HOUR_OF_DAY, -11);
        result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is("11h ago"));

        testDate.add(Calendar.HOUR_OF_DAY, -2);
        result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is("00:45"));

        testDate.add(Calendar.MINUTE, -46);
        result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is(String.format("%1$Td.%1$Tm.%1$TY %1$TH:%1$TM",testDate)));

        FormatUtil.truncateCalendar(testDate);
        testDate.add(Calendar.HOUR_OF_DAY, -11);
        result = FormatUtil.dateToText(mContext, nowDate.getTime(), testDate.getTime());
        assertThat(result, is(String.format("%1$Td.%1$Tm.%1$TY %1$TH:%1$TM",testDate)));

    }
}