package com.android.vendy.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.vendy.R;
import com.android.vendy.adapters.CategoriesListAdapter;
import com.android.vendy.screens.Fragments.Fragment_SignUp;
import com.bumptech.glide.Glide;

public class UploadVendorDetails extends AppCompatActivity {

    private static final int SELECT_IMAGE = 1213;
    RecyclerView categoriesList;
    ImageView documentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vendor_details);

        categoriesList = findViewById(R.id.categoryGridlist);
        documentImage = findViewById(R.id.proofDocument);

        categoriesList.setLayoutManager(new GridLayoutManager(UploadVendorDetails.this,4));
        categoriesList.setAdapter(new CategoriesListAdapter(UploadVendorDetails.this));
        categoriesList.setNestedScrollingEnabled(true);

        findViewById(R.id.uploadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == SELECT_IMAGE && resultCode == RESULT_OK){
            documentImage.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            Glide.with(UploadVendorDetails.this)
                    .load(uri)
                    .centerCrop()
                    .into(documentImage);
        }

    }
}
