package com.silentlad.bloodbank;

public class AppointmentCard {
    private String mAppointmentId;
    private int mImageResource;
    private String mHospitalId;
    private String mStartingTime;
    private String mDate;

    public AppointmentCard(String mAppointmentId, int mImageResource, String mHospitalId, String mStartingTime, String mDate) {
        this.mAppointmentId = mAppointmentId;
        this.mImageResource = mImageResource;
        this.mHospitalId = mHospitalId;
        this.mStartingTime = mStartingTime;
        this.mDate = mDate;
    }

    public String getmAppointmentId() {
        return mAppointmentId;
    }

    public String getmHospitalId() {
        return mHospitalId;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmStartingTime() {
        return mStartingTime;
    }

    public String getmDate() {
        return mDate;
    }
}
