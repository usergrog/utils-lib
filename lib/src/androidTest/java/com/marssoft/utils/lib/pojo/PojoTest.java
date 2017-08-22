package com.marssoft.utils.lib.pojo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.marssoft.utils.lib.UtilsLibConfig;
import com.marssoft.utils.lib.db.DataHelper;
import com.marssoft.utils.lib.helpers.PojoDbHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by alexey on 29-Nov-15.
 */
@RunWith(AndroidJUnit4.class)
public class PojoTest {

    DataHelper mDataHelper;
    AppLogEvent mPojo;

    @Before
    public void setUp() throws Exception {
        mDataHelper = DataHelper.getInstance(getTargetContext());
        UtilsLibConfig.sqLiteDatabase = mDataHelper.db;
        mPojo = new AppLogEvent();
        mPojo.setAppVersionCode(1);
        mPojo.setDeviceId("1234");
        mPojo.setDeviceName("test device");
        mPojo.setServerKey("1234");
        mPojo.setTag("tag");
        mPojo.setTitle("title");
        mPojo.setId(1l);
        mPojo.setTabletId("tablet id");
        long localId = mPojo.insert();
    }

    @After
    public void tearDown() throws Exception {
        mDataHelper.db.close();
        File db = new File(getTargetContext().getApplicationInfo().dataDir + "/databases/" + DataHelper.DB_NAME);
        boolean isDeleted = db.delete();
    }

    @Test
    public void testInsert() throws Exception {
        UtilsLibConfig.sqLiteDatabase = mDataHelper.db;
        AppLogEvent pojo = new AppLogEvent();
        pojo.setAppVersionCode(1);
        pojo.setDeviceId("1234");
        pojo.setDeviceName("test device");
        pojo.setServerKey("1234");
        pojo.setTag("tag");
        pojo.setTitle("title");
        pojo.setId(2l);
        pojo.setTabletId("tablet id");
        long localId = pojo.insert();
        assertThat(localId, is(2l));
        pojo.delete(AppLogEvent.class);
    }

    @Test
    public void testSelect() throws Exception {
        List<AppLogEvent> pojos = AppLogEvent.select("localId = ?", new String[]{String.valueOf(mPojo.getLocalId())}, null, AppLogEvent.class);
        assertThat(pojos.size(), is(1));
        AppLogEvent pojo = pojos.get(0);
        compareAppLogsObjects(pojo);
    }

    private void compareAppLogsObjects(AppLogEvent pojo) {
        assertTrue(pojo.getAppVersionCode() == mPojo.getAppVersionCode());
        assertTrue(pojo.getDeviceId().equals(mPojo.getDeviceId()));
        assertTrue(pojo.getServerKey().equals(mPojo.getServerKey()));
        assertTrue(pojo.getTag().equals(mPojo.getTag()));
        assertTrue(pojo.getTitle().equals(mPojo.getTitle()));
        assertTrue(pojo.getId().equals(mPojo.getId()));
        assertTrue(pojo.getTabletId().equals(mPojo.getTabletId()));
    }

    @Test
    public void testSelectOneById() throws Exception {
        AppLogEvent pojo = AppLogEvent.selectOne(mPojo.getLocalId(), AppLogEvent.class);
        compareAppLogsObjects(pojo);
    }

    @Test
    public void testSelectOneWithWhere() throws Exception {
        AppLogEvent pojo = AppLogEvent.selectOne("localId = ?", new String[]{String.valueOf(mPojo.getLocalId())}, AppLogEvent.class);
        compareAppLogsObjects(pojo);
    }

    @Test
    public void testSelectOneByIdNegative() throws Exception {
        AppLogEvent pojo = AppLogEvent.selectOne(999l, AppLogEvent.class);
        assertNull(pojo);
    }

    @Test
    public void testSelectOneWithWhereNegative() throws Exception {
        try {
            AppLogEvent pojo = AppLogEvent.selectOne("localId2 = ?", new String[]{String.valueOf(mPojo.getLocalId())}, AppLogEvent.class);
            fail("should throw exception");
        } catch (Exception ignored){}
    }


    @Test
    public void testCreateTable() throws Exception {
        PojoDbHelper.createTable(TestPojo.class, mDataHelper.db);
        Cursor tableInfo = UtilsLibConfig.sqLiteDatabase.rawQuery("pragma table_info(TestPojo)", null);
        List<String> dbTableColumns = new ArrayList<>();
        if (tableInfo.moveToFirst()) {
            do {
                dbTableColumns.add(tableInfo.getString(1));
            } while (tableInfo.moveToNext());
        }
        tableInfo.close();
        assertThat(dbTableColumns.size(), is(10));
    }


    @Test
    public void testAlterTable() throws Exception {
        UtilsLibConfig.sqLiteDatabase.execSQL("create table TestPojo2 (id INTEGER)");
        PojoDbHelper.alterTable(TestPojo2.class, mDataHelper.db);
        Cursor tableInfo = UtilsLibConfig.sqLiteDatabase.rawQuery("pragma table_info(TestPojo2)", null);
        List<String> dbTableColumns = new ArrayList<>();
        if (tableInfo.moveToFirst()) {
            do {
                dbTableColumns.add(tableInfo.getString(1));
            } while (tableInfo.moveToNext());
        }
        tableInfo.close();
        assertThat(dbTableColumns.size(), is(10));
    }

    @Test
    public void testAlterTable2() throws Exception {
        PojoDbHelper.alterTable(TestPojo3.class, mDataHelper.db);
        Cursor tableInfo = UtilsLibConfig.sqLiteDatabase.rawQuery("pragma table_info(TestPojo3)", null);
        List<String> dbTableColumns = new ArrayList<>();
        if (tableInfo.moveToFirst()) {
            do {
                dbTableColumns.add(tableInfo.getString(1));
            } while (tableInfo.moveToNext());
        }
        tableInfo.close();
        assertThat(dbTableColumns.size(), is(10));
    }


    @SuppressLint("ParcelCreator")
    private class TestPojo extends Pojo {
        public static final String TABLE_NAME = "TestPojo";
        int a;
        double b;
        String c;
        Date d;
        float e;
    }

    @SuppressLint("ParcelCreator")
    private class TestPojo2 extends Pojo{
        public static final String TABLE_NAME = "TestPojo2";
        int a;
        double b;
        String c;
        Date d;
        float e;
    }

    @SuppressLint("ParcelCreator")
    private class TestPojo3 extends Pojo{
        public static final String TABLE_NAME = "TestPojo3";
        int a;
        double b;
        String c;
        Date d;
        float e;
    }

}
