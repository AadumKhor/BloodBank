package com.silentlad.bloodbank.data.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.silentlad.bloodbank.data.HospitalContract.*;
import com.silentlad.bloodbank.data.Result;

import java.io.IOException;

public class DatabaseHelper_Hospitals extends SQLiteOpenHelper {
    private static final String DB_NAME = "Hospital.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + HospitalEntry.TABLE_NAME + " (" +
            HospitalEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
            HospitalEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_CITY + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_START_DAY + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_END_DAY + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_START_TIME + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_END_TIME + " TEXT NOT NULL, " +
            HospitalEntry.COLUMN_GMAPS + " TEXT NOT NULL" + ");";

    public DatabaseHelper_Hospitals(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HospitalEntry.TABLE_NAME);

        onCreate(db);
    }

    // method to insert data
    public boolean insertData(String id, String hospitalName, String city, String startDay,
                              String endDay, String startTime, String endTime,
                              String gmapsURL) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HospitalEntry.COLUMN_ID, id);
        contentValues.put(HospitalEntry.COLUMN_NAME, hospitalName);
        contentValues.put(HospitalEntry.COLUMN_CITY, city);
        contentValues.put(HospitalEntry.COLUMN_START_DAY, startDay);
        contentValues.put(HospitalEntry.COLUMN_END_DAY, endDay);
        contentValues.put(HospitalEntry.COLUMN_START_TIME, startTime);
        contentValues.put(HospitalEntry.COLUMN_END_TIME, endTime);
        contentValues.put(HospitalEntry.COLUMN_GMAPS, gmapsURL);

        long result = db.insert(HospitalEntry.TABLE_NAME, null, contentValues);

        return (result != -1);
    }

    public boolean updateData(String id, String hospitalName, String city, String startDay,
                              String endDay, String startTime, String endTime,
                              String gmapsURL) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HospitalEntry.COLUMN_ID, id);
        contentValues.put(HospitalEntry.COLUMN_NAME, hospitalName);
        contentValues.put(HospitalEntry.COLUMN_CITY, city);
        contentValues.put(HospitalEntry.COLUMN_START_DAY, startDay);
        contentValues.put(HospitalEntry.COLUMN_END_DAY, endDay);
        contentValues.put(HospitalEntry.COLUMN_START_TIME, startTime);
        contentValues.put(HospitalEntry.COLUMN_END_TIME, endTime);
        contentValues.put(HospitalEntry.COLUMN_GMAPS, gmapsURL);

        long result = db.update(HospitalEntry.TABLE_NAME, contentValues, "id=?", new String[]{id});
        return result != -1;
    }

    // method to get data
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + HospitalEntry.TABLE_NAME;

        return db.rawQuery(query, null);
    }

    public String[] getDetailsAdmin(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + HospitalEntry.TABLE_NAME + " where id=?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        String[] result = new String[8];
        while (cursor.moveToNext()) {
            result[0] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_ID));
            result[1] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_NAME));
            result[2] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_CITY));
            result[3] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_START_DAY));
            result[4] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_END_DAY));
            result[5] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_START_TIME));
            result[6] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_END_TIME));
            result[7] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_GMAPS));
        }
        cursor.close();
        return result;
    }

    public Result getHospitalDetailsAppointments(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name,city,gmap,time1,time2 FROM " + HospitalEntry.TABLE_NAME + " where id=?";
        Log.println(Log.DEBUG, "ID", "query made");
        Cursor cursor = db.rawQuery(query, new String[]{id});
        String[] result = new String[5];
        if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    result[0] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_NAME));
                    result[1] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_CITY));
                    result[2] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_GMAPS));
                    result[3] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_START_TIME));
                    result[4] = cursor.getString(cursor.getColumnIndex(HospitalEntry.COLUMN_END_TIME));
                }
                cursor.close();
                return new Result.Success<>(result);
        } else {
            return new Result.Error(new Exception("No data available"));
        }
    }

    public Result deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(HospitalEntry.TABLE_NAME, "id=?", new String[]{id});

        return new Result.Success<>(String.valueOf(result));
    }
}
