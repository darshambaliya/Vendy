package com.android.vendy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by the Sir Anku on 26-09-2019 at 06:23 AM .
 */
public class LoginModel {
    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
