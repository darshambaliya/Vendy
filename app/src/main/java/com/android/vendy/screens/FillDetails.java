package com.android.vendy.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.vendy.R;
import com.android.vendy.adapters.CategoriesListAdapter;

public class FillDetails extends AppCompatActivity {

    RecyclerView categoriesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);

        SharedPreferences preferences =getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();


        categoriesList = findViewById(R.id.categoryGridlist);
        categoriesList.setLayoutManager(new GridLayoutManager(FillDetails.this,4));
        categoriesList.setAdapter(new CategoriesListAdapter(FillDetails.this));

    }
}
