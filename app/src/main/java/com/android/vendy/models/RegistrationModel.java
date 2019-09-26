package com.android.vendy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by the Sir Anku on 26-09-2019 at 04:19 AM .
 */
public class RegistrationModel {
    public RegistrationModel(String mobileNo, String password, String password2) {
        this.mobileNo = mobileNo;
        this.password = password;
        this.password2 = password2;
    }

    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password2")
    @Expose
    private String password2;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
