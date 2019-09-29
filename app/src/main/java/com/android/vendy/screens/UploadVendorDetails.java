package com.android.vendy.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.vendy.R;
import com.android.vendy.adapters.CategoriesListAdapter;

public class UploadVendorDetails extends AppCompatActivity {
    RecyclerView categoriesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vendor_details);

        categoriesList = findViewById(R.id.categoryGridlist);

        categoriesList.setLayoutManager(new GridLayoutManager(UploadVendorDetails.this,4));
        categoriesList.setAdapter(new CategoriesListAdapter(UploadVendorDetails.this));
        categoriesList.setNestedScrollingEnabled(true);

    }
}
