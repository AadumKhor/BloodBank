package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.annotation.Nullable;

public class DonorLoginResult {
    @Nullable
    private DonorLoggedInUserView success;
    @Nullable
    private Integer error;

    public DonorLoginResult(@Nullable DonorLoggedInUserView success) {
        this.success = success;
    }

    public DonorLoginResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public DonorLoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
