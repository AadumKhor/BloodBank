package com.silentlad.bloodbank.donor_ui.appointments;

import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.silentlad.bloodbank.AppointmentCard;
import com.silentlad.bloodbank.HospitalCard;
import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.AppointmentContract;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Appointments;

import java.util.ArrayList;

public class AppointmentsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<HospitalCard>> liveHospitalList;
    private ArrayList<HospitalCard> hospitalList;
    private DatabaseHelper_Appointments databaseHelper_appointments;

    public AppointmentsViewModel(MutableLiveData<ArrayList<HospitalCard>> liveHospitalList, DatabaseHelper_Appointments db) {
        this.liveHospitalList = liveHospitalList;
        this.databaseHelper_appointments = db;
        init();
    }

    public MutableLiveData<ArrayList<HospitalCard>> getLiveHospitalList() {
        return liveHospitalList;
    }

    public void init(){
//        populateList();
        liveHospitalList.setValue(hospitalList);
    }

//    public void populateList(){
//        Cursor cursor = databaseHelper_appointments.getData();
//
//        if (cursor.getCount() == 0) {
//            return;
//        } else {
//            while (cursor.moveToNext()) {
////                appointmentList.add(new AppointmentCard(
////                        cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_ID)),
////                        R.drawable.ic_dashboard_black_24dp,
////                        cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_HOSPITAL_ID)),
////                        cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_TIME)),
////                        cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_DATE))
//
////                ));
//            }
//        }
//    }
}