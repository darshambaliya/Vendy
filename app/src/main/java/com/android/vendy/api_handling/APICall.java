package com.android.vendy.api_handling;

import android.util.Log;

import com.android.vendy.models.LoginModel;
import com.android.vendy.models.RegistrationModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by the Sir Anku on 26-09-2019 at 03:58 AM .
 */
public class APICall {

    private static final MediaType TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    private static final String SERVER = "https://vendy-api.herokuapp.com/";

    private final static String USER_REGISTRATION = "api/register"; //POST
    private final static String USER_LOGIN = "api/login"; //POST

    private static final String TAG = "API HANDLING";


    public static String RegisterUser(RegistrationModel registrationModel) {

        OkHttpClient client = new OkHttpClient();

        String json_body = new Gson().toJson(registrationModel);
        RequestBody body = RequestBody.create(json_body, TYPE_JSON);

        Request request = new Request.Builder()
                .url(SERVER + USER_REGISTRATION)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        Response response = null;
        String api_response = null;
        try {
            response = client.newCall(request).execute();
            api_response = response.body().string();
        } catch (IOException e) {
            Log.d(TAG, "RegisterUser: Exception "+e.getMessage());
            e.printStackTrace();
        }

        return api_response;

    }

    public static String LoginUser(LoginModel loginModel){

        OkHttpClient client = new OkHttpClient();

        String json_body = new Gson().toJson(loginModel);
        RequestBody body = RequestBody.create(json_body, TYPE_JSON);

        Request request = new Request.Builder()
                .url(SERVER + USER_LOGIN)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        Response response = null;
        String api_response = null;
        try {
            response = client.newCall(request).execute();
            api_response = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return api_response;
    }
}
