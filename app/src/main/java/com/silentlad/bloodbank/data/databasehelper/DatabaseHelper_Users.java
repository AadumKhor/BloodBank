package com.silentlad.bloodbank.data.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.UserContract;

import java.io.IOException;

public class DatabaseHelper_Users extends SQLiteOpenHelper {
    private static int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + "(" +
            UserContract.UserEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
            UserContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_PHONE + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_GENDER + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_AGE + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_WEIGHT + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_BG + " TEXT NOT NULL, " +
            UserContract.UserEntry.COLUMN_CITY + " TEXT NOT NULL" + ");";


    public DatabaseHelper_Users(Context context) {
        super(context, UserContract.UserEntry.TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);

        onCreate(db);
    }

    public Result getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE id=?";
        Log.println(Log.DEBUG, "id", query);
        Log.println(Log.DEBUG, "id", "Outside try getData");
        Cursor cursor = db.rawQuery(query, new String[]{id});
        try {
            Log.println(Log.DEBUG, "id", "Cursor is made");
            String[] results = new String[7];
            Log.println(Log.DEBUG, "id", "before while");
            while (cursor.moveToNext()) {
                results[0] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME));
                results[1] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_PHONE));
                results[2] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_GENDER));
                results[3] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_AGE));
                results[4] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_WEIGHT));
                results[5] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_BG));
                results[6] = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_CITY));
            }
            Log.println(Log.DEBUG, "id", "After while");
            cursor.close();
            return new Result.Success<>(results);
        } catch (Exception e) {
            Log.println(Log.DEBUG, "id", "Err ");
            return new Result.Error(new IOException("Error occurred", e));
        }

    }

    public Result dataExists(String name, String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.println(Log.DEBUG, "id", "before query");
        String query = "SELECT " + UserContract.UserEntry.COLUMN_ID + "," + UserContract.UserEntry.COLUMN_NAME + "," + UserContract.UserEntry.COLUMN_PHONE +
                " FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE name=? AND phone=?";
        Log.println(Log.DEBUG, "id", "After query");
        Cursor cursor = db.rawQuery(query, new String[]{name, phoneNumber});
        Log.println(Log.DEBUG, "id", "All good after cursor.");
        if (cursor.getCount() >= 1) {
            Log.println(Log.DEBUG, "id", "Result found");
            String id = "random";
            while (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_ID));
            }
            cursor.close();
            Log.println(Log.DEBUG, "id", id);
            return new Result.Success<String>(id);
        } else {
            cursor.close();
            Log.println(Log.DEBUG, "id", "No result ");
            return new Result.Error(new IOException("Data does not exist or has error"));
        }

    }

    public Result insertData(String id, String name, String phone, String gender,
                             String age, String weight, String bloodGroup, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.COLUMN_ID, id);
        cv.put(UserContract.UserEntry.COLUMN_NAME, name);
        cv.put(UserContract.UserEntry.COLUMN_PHONE, phone);
        cv.put(UserContract.UserEntry.COLUMN_GENDER, gender);
        cv.put(UserContract.UserEntry.COLUMN_AGE, age);
        cv.put(UserContract.UserEntry.COLUMN_WEIGHT, weight);
        cv.put(UserContract.UserEntry.COLUMN_BG, bloodGroup);
        cv.put(UserContract.UserEntry.COLUMN_CITY, city);

        long result = db.insert(UserContract.UserEntry.TABLE_NAME, null, cv);

        if (result != -1) {
            return new Result.Success<>(String.valueOf(result));
        } else {
            return new Result.Error(new IOException("Data cannot be added"));
        }
    }

    public Result updateData(String id, String name, String phone, String gender,
                             String age, String weight, String bloodGroup, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserContract.UserEntry.COLUMN_ID, id);
        cv.put(UserContract.UserEntry.COLUMN_NAME, name);
        cv.put(UserContract.UserEntry.COLUMN_PHONE, phone);
        cv.put(UserContract.UserEntry.COLUMN_GENDER, gender);
        cv.put(UserContract.UserEntry.COLUMN_AGE, age);
        cv.put(UserContract.UserEntry.COLUMN_WEIGHT, weight);
        cv.put(UserContract.UserEntry.COLUMN_BG, bloodGroup);
        cv.put(UserContract.UserEntry.COLUMN_CITY, city);

        int result = db.update(UserContract.UserEntry.TABLE_NAME, cv, "id=?", new String[]{id});

        return new Result.Success<>(String.valueOf(result));
    }

    public Result deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result;

        try {
            result = db.delete(UserContract.UserEntry.TABLE_NAME, "id=?", new String[]{id});
            return new Result.Success<>(String.valueOf(result));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error", e));
        }
    }
}
