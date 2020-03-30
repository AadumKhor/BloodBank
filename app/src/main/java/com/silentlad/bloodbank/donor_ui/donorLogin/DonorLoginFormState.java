package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.annotation.Nullable;

class DonorLoginFormState {
    @Nullable
    private Integer phoneNumberError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    public DonorLoginFormState(@Nullable Integer phoneNumberError, @Nullable Integer passwordError) {
        this.phoneNumberError = phoneNumberError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public DonorLoginFormState(boolean isDataValid) {
        this.phoneNumberError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getPhoneNumberError() {
        return phoneNumberError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
