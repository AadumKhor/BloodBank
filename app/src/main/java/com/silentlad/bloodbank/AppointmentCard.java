package com.silentlad.bloodbank;

public class AppointmentCard {
    private String mAppointmentId;
    private int mImageResource;
    private String mHospitalId;
    private String hosName;
    private String cityName;
    private String mStartingTime;
    private String mDate;

    public AppointmentCard(String mAppointmentId, int mImageResource, String mHospitalId, String hosName, String cityName
            ,String mStartingTime, String mDate) {
        this.mAppointmentId = mAppointmentId;
        this.mImageResource = mImageResource;
        this.mHospitalId = mHospitalId;
        this.hosName = hosName;
        this.cityName = cityName;
        this.mStartingTime = mStartingTime;
        this.mDate = mDate;
    }

    public String getHosName() {
        return hosName;
    }

    public String getCityName() {
        return cityName;
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
