package com.silentlad.bloodbank.data.model;

/**
 * Data class that captures hospital information.
 */

public class HospitalInfo {
    private String id;
    private String name;
    private String city;
    private String workingDayStart;
    private String workingDayEnd;
    private String workingHoursStart;
    private String workingHoursEnd;
    private String gMapsString;

    HospitalInfo(String id, String name, String city, String workingDayStart, String workingDayEnd, String workingHoursStart, String workingHoursEnd, String gMapsString) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.workingDayStart = workingDayStart;
        this.workingDayEnd = workingDayEnd;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.gMapsString = gMapsString;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkingDayStart() {
        return workingDayStart;
    }

    public void setWorkingDayStart(String workingDayStart) {
        this.workingDayStart = workingDayStart;
    }

    public String getWorkingDayEnd() {
        return workingDayEnd;
    }

    public void setWorkingDayEnd(String workingDayEnd) {
        this.workingDayEnd = workingDayEnd;
    }

    public String getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(String workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public String getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(String workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getgMapsString() {
        return gMapsString;
    }

    public void setgMapsString(String gMapsString) {
        this.gMapsString = gMapsString;
    }
}
