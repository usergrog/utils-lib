package com.marssoft.utils.lib.pojo;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.marssoft.utils.lib.FormatUtil;
import com.marssoft.utils.lib.UtilsLibConfig;
import com.marssoft.utils.lib.logs.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Alexey on 17.07.2015.
 */
@SuppressWarnings("TryWithIdenticalCatches")
public class Pojo implements Parcelable {
    public static final String TABLE_NAME = "pojo";

    private static final String TAG = Pojo.class.getSimpleName();

    protected long localId;
    protected Long id;
    protected String tabletId;
    protected Date createdAt;
    protected Date updatedAt;

    public Pojo() {
        createdAt = Calendar.getInstance().getTime();
        updatedAt = createdAt;
    }

    public static <T extends Pojo> List<T> select(String where, String[] whereArgs, String orderBy, Class<T> clazz) {

        StringBuilder sql = new StringBuilder("select * \nfrom ");
        sql.append(getTableName(clazz)).append("\n");
        if (where != null) {
            sql.append(" where ").append(where).append("\n");
        }
        if (orderBy != null) {
            sql.append(orderBy).append("\n");
        }
        Cursor cursor = UtilsLibConfig.sqLiteDatabase.rawQuery(sql.toString(), whereArgs);
        List<T> list = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    T pojo = clazz.newInstance();
                    //populate by reflection
                    Pojo.populateObject(pojo, cursor);
                    list.add(pojo);
                } while (cursor.moveToNext());
            }
        } catch (InstantiationException e) {
            Log.e(TAG, "error", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error", e);
        } finally {
            cursor.close();
        }
        return list;
    }

    public static <T extends Pojo> T selectOne(String where, String[] whereArgs, Class<T> clazz) {
        String sql = "select * from " + getTableName(clazz) + " where " + where;
        Cursor cursor = UtilsLibConfig.sqLiteDatabase.rawQuery(sql, whereArgs);
        T pojo = null;
        //noinspection TryFinallyCanBeTryWithResources
        try {
            if (cursor.moveToFirst()) {
                pojo = clazz.newInstance();
                //populate by reflection
                Pojo.populateObject(pojo, cursor);
            }
        } catch (InstantiationException e) {
            Log.e(TAG, "error", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error", e);
        } finally {
            cursor.close();
        }
        return pojo;
    }

    public static <T extends Pojo> T selectOne(long id, Class<T> clazz) {
        return selectOne("localId = ?", new String[]{String.valueOf(id)}, clazz);
    }

    public static <T extends Pojo> List<T> getLocalObjects(Class<T> clazz, int limit) {
        List<T> objects = new ArrayList<>();
        String sql = null;
        sql = "select * from " + getTableName(clazz) + " where coalesce(id,0) = 0";
        if (limit != 0) {
            sql = sql + " limit " + limit;
        }
        Cursor cursor = UtilsLibConfig.sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    T pojo = clazz.newInstance();
                    // populate by reflection
                    populateObject(pojo, cursor);
                    objects.add(pojo);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "error", e);
                } catch (InstantiationException e) {
                    Log.e(TAG, "error", e);
                } finally {
                    cursor.close();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return objects;
    }

    public static <T extends Pojo> Pojo loadFromPreferences(Context context, String prefName, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        SharedPreferences prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        T pojo = clazz.newInstance();
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

        for (Field field : fields) {
            Class fieldType = field.getType();
            String fieldTypeName = fieldType.getName();
            String fieldName = field.getName();
            if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers())) {
                try {
                    field.setAccessible(true);
/*
                    if (fieldTypeName.equalsIgnoreCase("[B")) {
                        field.set(pojo, prefs.getBlob(columnIndex));
                    }
*/
                    if (fieldTypeName.equalsIgnoreCase("int") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Integer")) {
                        field.set(pojo, prefs.getInt(fieldName, 0));
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.Long") ||
                            fieldTypeName.equalsIgnoreCase("long")) {
                        field.set(pojo, prefs.getLong(fieldName, 0));
                    }
                    if (fieldTypeName.equalsIgnoreCase("double") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Double")) {
                        field.set(pojo, Double.parseDouble(prefs.getString(fieldName, "0")));
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.util.Date")) {
                        long ldate = prefs.getLong(fieldName, 19000101000000L);
                        if (ldate > 0) {
                            Date date = FormatUtil
                                    .IntToDateTime(ldate);
                            if (date != null) {
                                field.set(pojo, date);
                            }
                        }
                    }

                    if (fieldType.getSuperclass() != null
                            && fieldType.getSuperclass().getName().equalsIgnoreCase("java.lang.Enum")) {
                        try {
                            @SuppressWarnings("unchecked")
                            Class<Enum> cl = (Class<Enum>) Class.forName(fieldTypeName);
                            String enumName = prefs.getString(fieldName, "");
                            if (!TextUtils.isEmpty(enumName)) {
                                field.set(pojo, Enum.valueOf(cl, enumName));
                            }
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.util.List")) {
                        // todo create list from String
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.String")) {
                        field.set(pojo, prefs.getString(fieldName, ""));
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.Boolean") ||
                            fieldTypeName.equalsIgnoreCase("boolean")) {
                        field.set(pojo, prefs.getBoolean(fieldName, false));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(TAG,
                            String.format("Error populating FieldName: %s FieldType: %s", fieldName,
                                    fieldTypeName), e);
                }
            }
        }
        return pojo;
    }

    public static void populateObject(Object obj, Cursor cursor) {
        Class clazz = obj.getClass();
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
            if (cursor.isClosed()) break;
            if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    int columnIndex = cursor.getColumnIndex(fieldName);
                    if (columnIndex >= 0) {
                        if (fieldTypeName.equalsIgnoreCase("[B")) {
                            field.set(obj, cursor.getBlob(columnIndex));
                        } else if (fieldTypeName.equalsIgnoreCase("int") ||
                                fieldTypeName.equalsIgnoreCase("java.lang.Integer")) {
                            field.set(obj, cursor.getInt(columnIndex));
                        } else if (fieldTypeName.equalsIgnoreCase("java.lang.Long") ||
                                fieldTypeName.equalsIgnoreCase("long")) {
                            field.set(obj, cursor.getLong(columnIndex));
                        } else if (fieldTypeName.equalsIgnoreCase("double") ||
                                fieldTypeName.equalsIgnoreCase("java.lang.Double")) {
                            field.set(obj, cursor.getDouble(columnIndex));
                        } else if (fieldTypeName.equalsIgnoreCase("java.util.Date")) {
                            long ldate = cursor.getLong(columnIndex);
                            if (ldate > 0) {
                                Date date = FormatUtil
                                        .IntToDateTime(ldate);
                                if (date != null) {
                                    field.set(obj, date);
                                }
                            }
                        } else if (fieldType.getSuperclass() != null
                                && fieldType.getSuperclass().getName().equalsIgnoreCase("java.lang.Enum")) {
                            try {
                                @SuppressWarnings("unchecked")
                                Class<Enum> cl = (Class<Enum>) Class.forName(fieldTypeName);
                                String enumName = cursor.getString(columnIndex);
                                if (enumName != null) {
                                    field.set(obj, Enum.valueOf(cl, enumName));
                                }
                            } catch (ClassNotFoundException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        } else if (fieldTypeName.equalsIgnoreCase("java.util.List")) {
                            // todo create list from String
                        } else if (fieldTypeName.equalsIgnoreCase("java.lang.String")) {
                            field.set(obj, cursor.getString(columnIndex));
                        } else if (fieldTypeName.equalsIgnoreCase("java.lang.Boolean") ||
                                fieldTypeName.equalsIgnoreCase("boolean")) {
                            field.set(obj, cursor.getInt(columnIndex) == 1);
                        } else {
                            try {
                                Gson gson = new Gson();
                                Class cl = Class.forName(fieldTypeName);
                                String objectJson = cursor.getString(columnIndex);
                                if (objectJson != null) {
                                    field.set(obj, gson.fromJson(objectJson, cl));
                                }
                            } catch (ClassNotFoundException e) {
                                Log.e(TAG, "error convert from json", e);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(TAG,
                            String.format("Error populating FieldName: %s FieldType: %s", fieldName,
                                    fieldTypeName), e);
                }
            }
        }
    }

    public static String getTableName(Class clazz) {
        String result = "oops";
        try {
            result = (String) clazz.getField("TABLE_NAME").get(null);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "error", e);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "error", e);
        }
        return result;
    }

    public static <T extends Pojo> void truncate(Class<T> clazz) {
        String tableName = getTableName(clazz);
        UtilsLibConfig.sqLiteDatabase.delete(tableName, null, null);
        UtilsLibConfig.sqLiteDatabase.delete("SQLITE_SEQUENCE", "name=?", new String[]{tableName});
    }

    public static <T extends Pojo> void updateServerId(List<IdPair> pairs, Class<T> clazz) {
        String tableName = getTableName(clazz);
        String sql = "update " + tableName + " set id = ? where localId = ?";
        SQLiteStatement update = UtilsLibConfig.sqLiteDatabase.compileStatement(sql);
        try {
            UtilsLibConfig.sqLiteDatabase.beginTransaction();
            for (IdPair pair : pairs) {
                update.bindLong(1, pair.getServerId());
                update.bindLong(2, pair.getLocalId());
                update.execute();
                /*Log.v(TAG, String.format("updated %s with local id %d, set server id %d", tableName,
                        pair.getLocalId(), pair.getServerId()));*/
            }
            UtilsLibConfig.sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            UtilsLibConfig.sqLiteDatabase.endTransaction();
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void saveToPreferences(Context context, Pojo object, String prefName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();
        Class clazz = object.getClass();
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        for (Field field : fields) {
            Class fieldType = field.getType();
            String fieldTypeName = fieldType.getName();
            String fieldName = field.getName();
            field.setAccessible(true);

            if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers()) &&
                    !fieldName.equalsIgnoreCase("stationId") &&
                    !fieldName.equalsIgnoreCase("localId") &&
                    !fieldName.equalsIgnoreCase("exposures")) {
                try {
//                    field.setAccessible(true);
                    /*if (fieldTypeName.equalsIgnoreCase("[B")) {
                        editor.putString(fieldName, String.(field.getByte(object)));
                    }*/
                    if (fieldTypeName.equalsIgnoreCase("int") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Integer")) {
                        editor.putInt(fieldName, field.getInt(object));
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.Long")) {
                        Object obj = field.get(object);
                        if (obj != null) {
                            Long l = (Long) obj;
                            editor.putLong(fieldName, l);
                        }
                    }
                    if (fieldTypeName.equalsIgnoreCase("long")) {
                        editor.putLong(fieldName, field.getLong(object));
                    }
                    if (fieldTypeName.equalsIgnoreCase("double") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Double")) {
                        editor.putString(fieldName, String.valueOf(field.getDouble(object)));
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.util.List")) {
                        List list = (List) field.get(object);
                        if (list != null) {
                            editor.putString(fieldName, list.toString());
                        }
                    }
                    if (fieldType.getSuperclass() != null
                            && fieldType.getSuperclass().getName().equalsIgnoreCase("java.lang.Enum")) {
                        Enum enumType = (Enum) field.get(object);
                        editor.putString(fieldName, enumType.toString());
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.util.Date")) {
                        Date date = (Date) field.get(object);
                        if (date != null) {
                            editor.putLong(fieldName, FormatUtil.DateTimeToInt(date));
                        }
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.String")) {
                        String str = (String) field.get(object);
                        if (str != null) {
                            editor.putString(fieldName, str);
                        }
                    }
                    if (fieldTypeName.equalsIgnoreCase("java.lang.Boolean") ||
                            fieldTypeName.equalsIgnoreCase("boolean")) {
                        editor.putBoolean(fieldName, field.getBoolean(object));
                    }
                } catch (IllegalAccessException e) {
                    Log.e(TAG,
                            String.format("Error populating FieldName: %s FieldType: %s", fieldName,
                                    fieldTypeName), e);
                }
            }
        }
        editor.apply();
    }

    public long insert() {
        if (localId > 0) {
            update();
            return localId;
        } else {
            if (UtilsLibConfig.sqLiteDatabase != null) {
                ContentValues cv = createContentValues();
                long id = UtilsLibConfig.sqLiteDatabase.insertOrThrow(getTableName(this.getClass()), null, cv);
                // todo insert all instances of Pojo
                setLocalId(id);
                return id;
            } else {
                return -1;
            }
        }
    }

    public void update() {
        setUpdatedAt(Calendar.getInstance().getTime());
        // populate by reflection
        ContentValues cv = createContentValues();
        int result = 0;
        result = UtilsLibConfig.sqLiteDatabase.update(getTableName(this.getClass()), cv, "localId = ?", new String[]{String.valueOf(getLocalId())});
        if (result == 0) {
            Log.w(TAG, String.format("can't update row with localId - %d, for table - %s", this.getLocalId(), getTableName(this.getClass())));
        }
    }

    public ContentValues createContentValues() {
        ContentValues cv = new ContentValues();

        Class clazz = this.getClass();
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
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers()) &&
                    field.getAnnotation(DbExclude.class) == null &&
                    !fieldName.equalsIgnoreCase("stationId") &&
                    !fieldName.equalsIgnoreCase("localId") &&
                    !fieldName.equalsIgnoreCase("exposures")) {
                try {
//                    field.setAccessible(true);
                    if (fieldTypeName.equalsIgnoreCase("[B")) {
                        cv.put(fieldName, field.getByte(this));
                    } else if (fieldTypeName.equalsIgnoreCase("int") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Integer")) {
                        cv.put(fieldName, field.getInt(this));
                    } else if (fieldTypeName.equalsIgnoreCase("java.lang.Long")) {
                        Long l = (Long) field.get(this);
                        cv.put(fieldName, l);
                    } else if (fieldTypeName.equalsIgnoreCase("long")) {
                        cv.put(fieldName, field.getLong(this));
                    } else if (fieldTypeName.equalsIgnoreCase("double") ||
                            fieldTypeName.equalsIgnoreCase("java.lang.Double")) {
                        cv.put(fieldName, field.getDouble(this));
                    } else if (fieldTypeName.equalsIgnoreCase("java.util.List")) {
                        List list = (List) field.get(this);
                        if (list != null) {
                            cv.put(fieldName, list.toString());
                        }
                    } else if (fieldType.getSuperclass() != null
                            && fieldType.getSuperclass().getName().equalsIgnoreCase("java.lang.Enum")) {
                        if (value != null) {
                            Enum enumType = (Enum) field.get(this);
                            cv.put(fieldName, enumType.toString());
                        }
                    } else if (fieldTypeName.equalsIgnoreCase("java.util.Date")) {
                        Date date = (Date) field.get(this);
                        if (date != null) {
                            cv.put(fieldName, FormatUtil.DateTimeToInt(date));
                        }
                    } else if (fieldTypeName.equalsIgnoreCase("java.lang.String")) {
                        String str = (String) field.get(this);
                        cv.put(fieldName, str);
                    } else if (fieldTypeName.equalsIgnoreCase("java.lang.Boolean") ||
                            fieldTypeName.equalsIgnoreCase("boolean")) {
                        cv.put(fieldName, field.getBoolean(this));
                    } else {
                        // try save as json
                        try {
                            Gson gson = new Gson();
                            cv.put(fieldName, gson.toJson(field.get(this)));
                        } catch (Exception e) {
                            Log.e(TAG, "error convert to json", e);
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.e(TAG,
                            String.format("Error populating FieldName: %s FieldType: %s", fieldName,
                                    fieldTypeName), e);
                }
            }
        }
        return cv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    public String getTabletId() {
        return tabletId;
    }

    public void setTabletId(String tabletId) {
        this.tabletId = tabletId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "localId=" + localId +
                ", id=" + id +
                ", tabletId='" + tabletId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static <T extends Pojo> void deleteById(long id, Class<T> clazz) {
        String tableName = getTableName(clazz);
        UtilsLibConfig.sqLiteDatabase.delete(tableName, "localId = ?", new String[]{String.valueOf(id)});
    }

    public <T extends Pojo> void delete(Class<T> clazz) {
        String tableName = getTableName(clazz);
        UtilsLibConfig.sqLiteDatabase.delete(tableName, "localId = ?", new String[]{String.valueOf(localId)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.localId);
        dest.writeValue(this.id);
        dest.writeString(this.tabletId);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    protected Pojo(Parcel in) {
        this.localId = in.readLong();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.tabletId = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }


}
