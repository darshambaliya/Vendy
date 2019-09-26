package com.android.vendy.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import com.android.vendy.R;
import com.android.vendy.screens.Fragments.Fragment_SignIn;
import com.android.vendy.screens.Fragments.Fragment_SignUp;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends FragmentActivity {
    private TabLayout signTabLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signTabLayout = findViewById(R.id.signTab);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Fragment_SignIn(), "Fragment SignIn");
        fragmentTransaction.commit();

        signTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition()) {

                    case 0:
                        fragmentTransaction.replace(R.id.fragment_container, new Fragment_SignIn(), "Fragment SignIn");
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.fragment_container, new Fragment_SignUp(), "Fragment SignUp");
                        fragmentTransaction.commit();

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
