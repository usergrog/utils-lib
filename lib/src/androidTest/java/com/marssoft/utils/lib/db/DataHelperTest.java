package com.marssoft.utils.lib.db;

import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by alexey on 29-Nov-15.
 */
@RunWith(AndroidJUnit4.class)
public class DataHelperTest {

    DataHelper mDataHelper;

    @Before
    public void setUp() throws Exception {
        mDataHelper = DataHelper.getInstance(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        mDataHelper.db.close();
        File db = new File(getTargetContext().getApplicationInfo().dataDir + "/databases/" + DataHelper.DB_NAME);
        boolean isDeleted = db.delete();
    }

    @Test
    public void testOnCreate() throws Exception {
        assertNotNull(mDataHelper);
        Cursor cursor = mDataHelper.db.rawQuery("pragma table_info(\"AppLogs\")", null);
        if (cursor.moveToFirst()) {
            assertThat(cursor.getCount(), is(11));
        } else {
            fail("AppLogs table didn't create");
        }
    }

    @Test
    public void testOnUpgrade() throws Exception {
        assertNotNull(mDataHelper);
        mDataHelper.onUpgrade(mDataHelper.db, 1, 2);
        Cursor cursor = mDataHelper.db.rawQuery("pragma table_info(\"AppLogs\")", null);
        assertThat(cursor.getCount(), is(12));
        if (cursor.moveToFirst()) {
            cursor.move(11);
            assertThat(cursor.getString(1), is("testColumn"));
        } else {
            fail("AppLogs table didn't create");
        }
    }

    @Test
    public void testFullUpgrade() throws Exception {
        assertNotNull(mDataHelper);

        mDataHelper.db.close();
        File db = new File(getTargetContext().getApplicationInfo().dataDir + "/databases/" + DataHelper.DB_NAME);
        boolean isDeleted = db.delete();
        assertTrue(isDeleted);
        mDataHelper = DataHelper.getInstance(getTargetContext());
        assertNotNull(mDataHelper);

        mDataHelper.onUpgrade(mDataHelper.db, 1, 3);// change here full range of updates
        Cursor cursor = mDataHelper.db.rawQuery("pragma table_info(\"AppLogs\")", null);
        assertThat(cursor.getCount(), is(13));
        if (cursor.moveToFirst()) {
            cursor.move(11);
            assertThat(cursor.getString(1), is("testColumn"));
            cursor.moveToNext();
            assertThat(cursor.getString(1), is("testColumn2"));
        } else {
            fail("AppLogs table didn't create");
        }
    }

}