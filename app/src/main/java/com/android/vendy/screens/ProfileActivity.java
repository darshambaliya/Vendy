package com.android.vendy.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.android.vendy.R;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    ImageButton hamBurgerImg;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawer = findViewById(R.id.draw_layout);
        hamBurgerImg =  findViewById(R.id.hamburgerImg);
        hamBurgerImg.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("WrongConstant")
            public void onClick(View v)
            {
                if(!drawer.isDrawerOpen(Gravity.START))
                    drawer.openDrawer(Gravity.START);
                else
                    drawer.closeDrawer(Gravity.END);
            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(3).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return true;
            }
        });

    }
}
