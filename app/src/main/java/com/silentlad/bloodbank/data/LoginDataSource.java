package com.silentlad.bloodbank.data;

import com.silentlad.bloodbank.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result login(String username, String password) {
        if (username.equals("blooddonation.app0@gmail.com") && password.equals("123456")) {
            try {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(fakeUser);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        } else {
            return new Result.Error(new IOException("Error logging you in"));
        }
    }
}
