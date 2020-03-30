package com.silentlad.bloodbank.admin_ui.home;

import android.database.Cursor;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.silentlad.bloodbank.HospitalCard;
import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.HospitalContract;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<HospitalCard>> liveHospitalList;
    private ArrayList<HospitalCard> hospitalList;
    private DatabaseHelper_Hospitals db;

    public HomeViewModel(MutableLiveData<ArrayList<HospitalCard>> liveHospitalList, DatabaseHelper_Hospitals db) {
        this.liveHospitalList = liveHospitalList;
        this.db = db;
        init();
    }

    public MutableLiveData<ArrayList<HospitalCard>> getLiveHospitalList() {
        return liveHospitalList;
    }

    private void init() {
        populateList();
        liveHospitalList.setValue(hospitalList);
    }

    private void populateList() {
        Cursor cursor = db.viewData();

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                hospitalList.add(new HospitalCard(cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_ID)),
                        R.drawable.ic_local_hospital_black_24dp,
                        cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_CITY)),
                        new String[]{cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_START_DAY)),
                                cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_END_DAY))},
                        new String[]{cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_START_TIME)),
                                cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_END_TIME))},
                        cursor.getString(cursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_GMAPS))
                ));
            }

        }
    }
}