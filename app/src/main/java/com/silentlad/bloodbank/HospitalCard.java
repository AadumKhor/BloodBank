package com.silentlad.bloodbank;

public class HospitalCard {
    private  String id;
    private int mImageResource;
    private String hospitalName;
    private String city;
    private String[] workingDays;
    private String[] workingHours;
    private String gMaps;

    public HospitalCard(String id, int mImageResource, String hospitalName, String city, String[] workingDays, String[] workingHours, String gMaps) {
        this.id = id;
        this.mImageResource = mImageResource;
        this.hospitalName = hospitalName;
        this.city = city;
        this.workingDays = workingDays;
        this.workingHours = workingHours;
        this.gMaps = gMaps;
    }

    public String getId() {
        return id;
    }

    public String[] getWorkingDays() {
        return workingDays;
    }

    public String[] getWorkingHours() {
        return workingHours;
    }

    public String getgMaps() {
        return gMaps;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getCity() {
        return city;
    }
}
