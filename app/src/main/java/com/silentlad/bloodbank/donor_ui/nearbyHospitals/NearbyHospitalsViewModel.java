package com.silentlad.bloodbank.donor_ui.nearbyHospitals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearbyHospitalsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearbyHospitalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}