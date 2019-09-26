package com.android.vendy.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.vendy.R;

import static com.android.vendy.Global.PREF_TOKEN;

/**
 * Created by the Sir Anku on 24-07-2019 at 11:48 AM .
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        SharedPreferences sharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);

        final String token = sharedPref.getString(PREF_TOKEN,"");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!token.isEmpty()){
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                }else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }

                finish();
            }
        },5*100);
    }
}
