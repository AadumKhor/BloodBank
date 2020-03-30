package com.silentlad.bloodbank.data;

import android.content.Context;
import android.util.Log;

import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Users;
import com.silentlad.bloodbank.data.model.DonorLoggedInUser;
import com.silentlad.bloodbank.data.model.LoggedInUser;

import java.io.IOException;

public class DonorLoginDataSource {

    public Result login(String name, String phone, Context context) {
        DatabaseHelper_Users db_users = new DatabaseHelper_Users(context);
        Log.println(Log.DEBUG, "id", "Method called");
        if (db_users.dataExists(name, phone) instanceof Result.Success) {
            String result = (String) ((Result.Success) db_users.dataExists(name, phone)).getData();
            Log.println(Log.DEBUG, "id", result);
            try {
                Log.println(Log.DEBUG, "id", "Inside try");
                DonorLoggedInUser fakeUser = new DonorLoggedInUser(
                        result, name, phone
                );
                return new Result.Success<>(fakeUser);
            } catch (Exception e) {
                Log.println(Log.DEBUG, "id", "Exception");
                return new Result.Error(new IOException("Error logging in", e));
            }
        } else {
            return new Result.Error(new IOException("Error logging in"));
        }
    }

    public void logout() {
        //TODO: Implement logout
    }
}
