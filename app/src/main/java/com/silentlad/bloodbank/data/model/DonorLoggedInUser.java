package com.silentlad.bloodbank.data.model;

public class DonorLoggedInUser {
    private String phoneNumber;
    private String donorId;
    private String displayName;
    private String gender;
    private String age;
    private String weight;
    private String bloodGroup;
    private String city;


    public DonorLoggedInUser(String phoneNumber, String donorId, String displayName, String gender, String age, String weight, String bloodGroup, String city) {
        this.phoneNumber = phoneNumber;
        this.donorId = donorId;
        this.displayName = displayName;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.bloodGroup = bloodGroup;
        this.city = city;
    }

    public DonorLoggedInUser(String id, String name, String phone) {
        this.displayName = name;
        this.donorId = id;
        this.phoneNumber = phone;
    }

    public DonorLoggedInUser(String name, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.displayName = name;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDonorId() {
        return donorId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
