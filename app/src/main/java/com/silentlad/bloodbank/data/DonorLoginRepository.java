package com.silentlad.bloodbank.data;

import android.content.Context;

import com.silentlad.bloodbank.data.model.DonorLoggedInUser;

public class DonorLoginRepository {
    private static volatile DonorLoginRepository instance;

    private DonorLoginDataSource dataSource;

    private DonorLoggedInUser user = null;

    public DonorLoginRepository(DonorLoginDataSource dataSource){this.dataSource = dataSource;}

    public static DonorLoginRepository getInstance(DonorLoginDataSource dataSource){
        if(instance == null){
            instance = new DonorLoginRepository((dataSource));
        }
        return instance;
    }

    public boolean isLoggedIn() {return user != null;}

    public void logout(){
        user = null;
        dataSource.logout();
    }

    public DonorLoggedInUser getLoggedInUser(){
        return this.user;
    }

    private void setLoggedInUser(DonorLoggedInUser user){
        this.user = user;
    }

    public Result<DonorLoggedInUser> login(String name , String password, Context context){
        Result result = dataSource.login(name , password, context);
        if(result instanceof Result.Success){
            setLoggedInUser(((Result.Success<DonorLoggedInUser>)result).getData());
        }
        return result;
    }
}
