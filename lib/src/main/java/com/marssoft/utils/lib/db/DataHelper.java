package com.marssoft.utils.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

/**
 * Created by alexey on 29-Nov-15.
 */
public class DataHelper extends SQLiteOpenHelper {
    /**
     * Version of database. Change this number if you changed structure of database.
     */
    private static final int DB_VERSION = 4;
    private static final String TAG = DataHelper.class.getSimpleName();

    public static String DB_NAME = "Content.db";
    private static DataHelper instance;
    //    private static String DB_PATH = Preferences.appFolder + "/db/" + DB_NAME;
    public SQLiteDatabase db;
    private Context mContext;

    private DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        this.db = getWritableDatabase();
        Log.d(TAG, "DB opened");
        setDBPragmas();
    }

    public static DataHelper getInstance(Context context) throws IOException {
        if (instance == null) {
            instance = new DataHelper(context);
        }
        if (!instance.db.isOpen()) {
            instance.db = instance.getWritableDatabase();
        }
        return instance;
    }

    private void setDBPragmas() {
        if (!this.db.isReadOnly()) {
            // Enable foreign key constraints
            this.db.execSQL("PRAGMA foreign_keys=ON");
        }
    }

    /**
     * We will run creation script from assets
     *
     * @param sqliteDB instance of new database
     */
    @Override
    public void onCreate(SQLiteDatabase sqliteDB) {
        Log.d(TAG, "Creating database");
        try {
            SqlExecutor.execSQLFile(mContext, sqliteDB, "AppLogs.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDB, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("Upgrade database, previous version %d, new version %d.", oldVersion,
                newVersion));
        try {
            runUpdateScripts(sqliteDB, oldVersion, newVersion);
        } catch (Exception e) {
            Log.w(TAG, "DataHelper.onUpgrade", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void close() {
        Log.d(TAG, "Close DB");
        this.db.close();
        super.close();
    }

    @Override
    protected void finalize() throws Throwable {
        if (db != null) {
            db.close();
        }
        super.finalize();
    }

    /**
     * If existing database has older version than the application,
     * we will go through all updateXX.sql script-files and execute them
     *
     * @param sqliteDB   instance of new database
     * @param oldVersion version of database
     * @param newVersion version of application
     */
    private void runUpdateScripts(SQLiteDatabase sqliteDB, int oldVersion, int newVersion)
            throws IOException {
        for (int i = oldVersion + 1; i <= newVersion; i++) {
            SqlExecutor.execSQLFile(mContext, sqliteDB, "update" + i + ".sql");
            sqliteDB.execSQL("PRAGMA user_version = " + i);
        }
    }


}
