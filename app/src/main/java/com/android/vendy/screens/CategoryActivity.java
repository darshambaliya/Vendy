package com.android.vendy.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.vendy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ListView categoriesListView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        builder = new AlertDialog.Builder(this);
        String[] categoryArray = new String[]{
                "Panipuri", "Chinese", "South Indian", "Fast-Food", "Soda", "Tea & Coffee", "Fruit Juice", "Dabeli", "Bakery", "Puff & Sandwiches", "Omlet"
        };
        categoriesListView = findViewById(R.id.listView);
        List<String> categoryList = new ArrayList<>(Arrays.asList(categoryArray));
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
        categoriesListView.setAdapter(ad);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                startActivity(new Intent(CategoryActivity.this, MapsMarkerActivity.class));
                /*AlertDialog alert = builder.create();
                alert.setTitle("Google Maps API");
                alert.setMessage("Clicking this will open Google Maps with corresponding vendor markers");
                alert.show();*/
            }
        });


    }

    public void backClick(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
        finish();
    }
}

