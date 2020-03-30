package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.silentlad.bloodbank.data.DonorLoginDataSource;
import com.silentlad.bloodbank.data.DonorLoginRepository;
import com.silentlad.bloodbank.data.LoginRepository;

public class DonorLoginViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DonorLoginViewModel.class)){
            return (T) new DonorLoginViewModel(DonorLoginRepository.getInstance(new DonorLoginDataSource()));
        }else{
            throw new IllegalArgumentException("Unknown viewModel class");
        }
    }
}
