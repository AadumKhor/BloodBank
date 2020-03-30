package com.silentlad.bloodbank.donor_ui.donorLogin;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.DonorLoginRepository;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.model.DonorLoggedInUser;

public class DonorLoginViewModel extends ViewModel {
    private MutableLiveData<DonorLoginFormState> donorLoginFormState = new MutableLiveData<>();
    private MutableLiveData<DonorLoginResult> donorLoginResult = new MutableLiveData<>();
    private DonorLoginRepository loginRepository;

    public DonorLoginViewModel(DonorLoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<DonorLoginFormState> getDonorLoginFormState() {
        return donorLoginFormState;
    }

    public LiveData<DonorLoginResult> getDonorLoginResult() {
        return donorLoginResult;
    }

    public DonorLoggedInUser getLoggedInUser(){
        return loginRepository.getLoggedInUser();
    }

    public void login(String phoneNumber, String password, Context context) {
        Result<DonorLoggedInUser> result = loginRepository.login(phoneNumber, password, context);

        if (result instanceof Result.Success) {
            DonorLoggedInUser data = (((Result.Success<DonorLoggedInUser>) result).getData());
            donorLoginResult.setValue(new DonorLoginResult(new DonorLoggedInUserView(data.getDisplayName())));
        } else {
            donorLoginResult.setValue(new DonorLoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String phoneNumber, String password) {
        if (!isPhoneNumberValid(phoneNumber)) {
            donorLoginFormState.setValue(new DonorLoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            donorLoginFormState.setValue(new DonorLoginFormState(null, R.string.invalid_password));
        } else {
            donorLoginFormState.setValue(new DonorLoginFormState(true));
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
