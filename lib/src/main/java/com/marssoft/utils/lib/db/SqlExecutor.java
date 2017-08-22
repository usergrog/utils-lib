package com.marssoft.utils.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alexey on 29-Nov-15.
 */
public class SqlExecutor {
    private static final String TAG = SqlExecutor.class.getSimpleName();

    public static void execSQLFile(Context context, SQLiteDatabase sqliteDB, String scriptPath) throws IOException {
        InputStream is = context.getAssets().open(scriptPath);
        String str;
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is, "UTF8");
        BufferedReader reader = new BufferedReader(sr);
        while ((str = reader.readLine()) != null) {
            if (str.length() > 1 && !str.substring(0, 2).equals("--")) {
                String l = str.substring(str.length() - 1);//(str.length() - 1, str.length());
                if (l.equals(";")) {
                    sb.append(str);
                    try {
                        sqliteDB.execSQL(sb.toString());
                    } catch (Exception e) {
                        Log.w(TAG, "error executing sql statement: \n " + sb.toString(), e);
                        throw new RuntimeException(e.getMessage());
                    }
                    sb = new StringBuilder();
                } else {
                    sb.append(str).append("\n");
                }
            }
        }
    }
}



