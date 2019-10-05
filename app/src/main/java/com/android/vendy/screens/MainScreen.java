package com.android.vendy.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.vendy.R;
import com.google.android.material.navigation.NavigationView;


public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    private DrawerLayout drawer;
    private NavigationView navigationView;
    ImageButton hamburgerBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        drawer = findViewById(R.id.draw_layout);
        hamburgerBtn = findViewById(R.id.hamburgerImg);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(MainScreen.this);

        hamburgerBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(Gravity.START))
                    drawer.openDrawer(Gravity.START);
                else
                    drawer.closeDrawer(Gravity.START);
            }
        });


    }

    public void openCategory(View view) {
        Intent intent = new Intent(MainScreen.this, CategoryActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(MainScreen.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemId = menuItem.getItemId();

        if (itemId == R.id.signOut){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(MainScreen.this, EntranceActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            },800);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
