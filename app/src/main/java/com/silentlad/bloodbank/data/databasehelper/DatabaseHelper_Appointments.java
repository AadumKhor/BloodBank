package com.silentlad.bloodbank.data.databasehelper;
//TODO: Implement this

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.silentlad.bloodbank.data.AppointmentContract.*;
import com.silentlad.bloodbank.data.Result;

import java.io.IOException;

public class DatabaseHelper_Appointments extends SQLiteOpenHelper {

    private static final String DB_NAME = "Appointments.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + AppointmentEntry.TABLE_NAME + "(" +
            AppointmentEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
            AppointmentEntry.COLUMN_HOSPITAL_ID + " TEXT NOT NULL, " +
            AppointmentEntry.COLUMN_USER_ID + " TEXT NOT NULL, " +
            AppointmentEntry.COLUMN_TIME + " TEXT NOT NULL, " +
            AppointmentEntry.COLUMN_DATE + " TEXT NOT NULL" + ")";


    public DatabaseHelper_Appointments(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppointmentEntry.TABLE_NAME);

        onCreate(db);
    }

    public Result insert(String id, String hospitalId, String userId, String time, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AppointmentEntry.COLUMN_ID, id);
        contentValues.put(AppointmentEntry.COLUMN_HOSPITAL_ID, hospitalId);
        contentValues.put(AppointmentEntry.COLUMN_USER_ID, userId);
        contentValues.put(AppointmentEntry.COLUMN_TIME, time);
        contentValues.put(AppointmentEntry.COLUMN_DATE, date);

        long result = db.insert(AppointmentEntry.TABLE_NAME, null, contentValues);
        if (result != -1) {
            return new Result.Success<>("Data inserted");
        } else {
            return new Result.Error(new IOException("Data not inserted"));
        }
    }

    public Result updateData(String appointmentId, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AppointmentEntry.COLUMN_TIME, newTime);
        cv.put(AppointmentEntry.COLUMN_DATE, newDate);

        try {
            db.update(AppointmentEntry.TABLE_NAME, cv, "appointmentId=?", new String[]{appointmentId});
            return new Result.Success<>("Updated data.");
        } catch (Exception e) {
            return new Result.Error(new IOException("Not updated."));
        }
    }

    public boolean checkIfExists(String hospitalId, String userId, String time, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + AppointmentEntry.COLUMN_ID + " FROM " + AppointmentEntry.TABLE_NAME + " WHERE " +
                AppointmentEntry.COLUMN_HOSPITAL_ID + "= ?"
                + " AND " + AppointmentEntry.COLUMN_USER_ID + "= ?"
                + " AND " + AppointmentEntry.COLUMN_TIME + "= ?"
                + " AND " + AppointmentEntry.COLUMN_DATE + "= ?";
        Log.println(Log.DEBUG, "query", query);
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{hospitalId, userId, time, date});
        Log.println(Log.DEBUG, "query", String.valueOf(cursor.getCount()));
//        cursor.close();
        return cursor.getCount() > 1;
    }

    public void removeItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AppointmentEntry.TABLE_NAME, "appointmentId=?", new String[]{id});
    }


    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + AppointmentEntry.TABLE_NAME + " WHERE userId=?";
        return db.rawQuery(query, new String[]{id});
    }
}
