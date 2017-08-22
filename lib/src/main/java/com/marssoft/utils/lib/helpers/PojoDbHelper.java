package com.marssoft.utils.lib.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.marssoft.utils.lib.pojo.Pojo;

/**
 * Created by alexey on 21-Dec-15.
 */
public class PojoDbHelper {
    private static final String TAG = PojoDbHelper.class.getSimpleName();

    public static <T extends Pojo> void createTable(Class<T> clazz, SQLiteDatabase sqLiteDatabase) {
        Set<Field> fields = new HashSet<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class superClass = clazz.getSuperclass();
        String tableName = Pojo.getTableName(clazz);
        do {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        } while (superClass != null && !superClass.getSimpleName().equals("Object"));

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (").append("\n");
        boolean firstField = true;
        for (Field field : fields) {
            Class fieldType = field.getType();
            String fieldName = field.getName();
            if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                if (!firstField) {
                    sb.append(",").append("\n");
                }
                sb.append("   ").append(fieldName).append(" ").append(javaToDbType(fieldType));
                if (fieldName.equals("localId")) {
                    sb.append(" PRIMARY KEY ASC AUTOINCREMENT");
                }
                firstField = false;
            }
        }
        sb.append(")");
        sqLiteDatabase.execSQL(sb.toString());
        // add indexes
        sb = new StringBuilder();
        sb.append("CREATE INDEX IF NOT EXISTS i_").append(tableName).append("_id ON ").append(tableName).append(" (id asc)");
        sqLiteDatabase.execSQL(sb.toString());
    }

    public static <T extends Pojo> void alterTable(Class<T> clazz, SQLiteDatabase sqLiteDatabase) {
        // compare java class and table in DB
        String tableName = Pojo.getTableName(clazz);
        Set<String> dbTableColumns = new HashSet<>();
        Cursor tableInfo = sqLiteDatabase.rawQuery("pragma table_info(" + tableName + ")", null);
        if (tableInfo.moveToFirst()) {
            do {
                dbTableColumns.add(tableInfo.getString(1));
            } while (tableInfo.moveToNext());
        } else {
            // create table
            createTable(clazz, sqLiteDatabase);
        }
        tableInfo.close();
        if (dbTableColumns.size() > 0) {
            Set<Field> fields = new HashSet<>();
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            Class superClass = clazz.getSuperclass();
            do {
                fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
                superClass = superClass.getSuperclass();
            } while (superClass != null && !superClass.getSimpleName().equals("Object"));
            for (Field field : fields) {
                Class fieldType = field.getType();
                String fieldTypeName = fieldType.getName();
                String fieldName = field.getName();
                if (!Modifier.isStatic(field.getModifiers()) &&
                        !Modifier.isFinal(field.getModifiers()) &&
                        !dbTableColumns.contains(fieldName)) {
                    field.setAccessible(true);
                    StringBuilder sb = new StringBuilder();
                    sb.append("ALTER TABLE ").append(tableName).append(" ADD COLUMN ")
                            .append(fieldName).append(" ").append(javaToDbType(fieldType));
                    Log.v(TAG, sb.toString());
                    sqLiteDatabase.execSQL(sb.toString());
                }
            }
        }
    }

    private static String javaToDbType(Class fieldType) {
        String dbType = "VARCHAR";
        String fieldTypeName = fieldType.getName();
        if (fieldTypeName.equalsIgnoreCase("[B")) {
            dbType = "BLOB";
        } else if (fieldTypeName.equalsIgnoreCase("int") ||
                fieldTypeName.equalsIgnoreCase("java.lang.Integer") ||
                fieldTypeName.equalsIgnoreCase("java.lang.Long") ||
                fieldTypeName.equalsIgnoreCase("long") ||
                fieldTypeName.equalsIgnoreCase("java.util.Date") ||
                fieldTypeName.equalsIgnoreCase("java.lang.Boolean") ||
                fieldTypeName.equalsIgnoreCase("boolean")) {
            dbType = "INTEGER";
        } else if (fieldTypeName.equalsIgnoreCase("double") ||
                fieldTypeName.equalsIgnoreCase("java.lang.Double")) {
            dbType = "NUMERIC";
        } else if ((fieldType.getSuperclass() != null
                && fieldType.getSuperclass().getName().equalsIgnoreCase("java.lang.Enum")) ||
                fieldTypeName.equalsIgnoreCase("java.lang.String")) {
            dbType = "VARCHAR";
        }
        return dbType;
    }
}
