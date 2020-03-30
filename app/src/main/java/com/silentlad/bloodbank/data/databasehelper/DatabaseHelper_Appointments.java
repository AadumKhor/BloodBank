package com.silentlad.bloodbank.data.databasehelper;
//TODO: Implement this

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silentlad.bloodbank.data.AppointmentContract.*;

public class DatabaseHelper_Appointments extends SQLiteOpenHelper {

    private static final String DB_NAME = "Appointments.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + AppointmentEntry.TABLE_NAME + "(" +
            AppointmentEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
            AppointmentEntry.COLUMN_HOSPITAL_ID + " TEXT NOT NULL, " +
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

    public boolean insert(String id, String hospitalId, String time, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AppointmentEntry.COLUMN_ID, id);
        contentValues.put(AppointmentEntry.COLUMN_HOSPITAL_ID, hospitalId);
        contentValues.put(AppointmentEntry.COLUMN_TIME, time);
        contentValues.put(AppointmentEntry.COLUMN_DATE, date);

        long result = db.insert(AppointmentEntry.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean updateData(String appointmentId, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AppointmentEntry.COLUMN_TIME, newTime);
        cv.put(AppointmentEntry.COLUMN_DATE, newDate);

        long result = db.update(AppointmentEntry.TABLE_NAME, cv, "appointmentId=?", new String[]{appointmentId});

        return result != -1;
    }

    public void removeItem(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(AppointmentEntry.TABLE_NAME, "appointmentId=?", new String[]{id});
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + AppointmentEntry.TABLE_NAME;
        return db.rawQuery(query, null);
    }

}
