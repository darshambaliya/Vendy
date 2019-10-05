package com.android.vendy.screens.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.vendy.api_handling.APICall;
import com.android.vendy.R;
import com.android.vendy.models.RegistrationModel;
import com.android.vendy.screens.MainScreen;
import com.android.vendy.screens.UploadVendorDetails;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.android.vendy.Global.PREF_TOKEN;

/**
 * Created by the Sir Anku on 26-09-2019 at 02:49 AM .
 */
public class Fragment_SignUp extends Fragment {

    private static final String TAG = Fragment_SignUp.class.getSimpleName();
    private static final int SELECT_IMAGE = 1213;
    EditText mobileNo_ET, fname_ET, lname_ET, pwd_ET, cpwd_ET;
    CardView registerBtn;
    SharedPreferences sharedPreferences;
    CheckBox applyAsVendorCheckBox;
    Context context;
    View view;
    ImageView profile_pic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "myprefs", Context.MODE_PRIVATE);

        view = getView();


        mobileNo_ET = view.findViewById(R.id.mob_noET);
        fname_ET = view.findViewById(R.id.fname_ET);
        lname_ET = view.findViewById(R.id.lname_ET);
        pwd_ET = view.findViewById(R.id.pwd_ET);
        cpwd_ET = view.findViewById(R.id.cpwd_ET);
        registerBtn = view.findViewById(R.id.regBtn);
        applyAsVendorCheckBox = view.findViewById(R.id.applyCB);
        profile_pic = view.findViewById(R.id.pro_pic);

        view.findViewById(R.id.select_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO WORK ON VALIDATION
                if (mobileNo_ET.getText().toString().length() == 10) {

                    if (pwd_ET.getText().toString().equals(cpwd_ET.getText().toString())) {

                        if (applyAsVendorCheckBox.isChecked()) {

                            if (!mobileNo_ET.getText().toString().isEmpty() && !pwd_ET.getText().toString().isEmpty()) {
                                Intent toUploadVendorDetails = new Intent(context, UploadVendorDetails.class);
                                toUploadVendorDetails.putExtra("mobile_no", mobileNo_ET.getText().toString());
                                toUploadVendorDetails.putExtra("password", pwd_ET.getText().toString());
                                context.startActivity(toUploadVendorDetails);
                            } else {

                                fieldsValidation();
                            }

                        } else {

                            if (!mobileNo_ET.getText().toString().isEmpty() && !pwd_ET.getText().toString().isEmpty()) {
                                RegistrationModel registrationModel = new RegistrationModel(mobileNo_ET.getText().toString(),
                                        pwd_ET.getText().toString(),
                                        cpwd_ET.getText().toString());
                                new RegisterUser(registrationModel).execute();

                            } else {

                                fieldsValidation();
                            }

                        }

                    } else {
                        Toast.makeText(getContext(), "Password must match.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Enter a valid mobile number.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void fieldsValidation() {
        if (mobileNo_ET.getText().toString().isEmpty()) {
            Toast.makeText(context, "Mobile number must not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd_ET.getText().toString().isEmpty()) {
            Toast.makeText(context, "Password must not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            Glide.with(Fragment_SignUp.this)
                    .load(uri)
                    .centerCrop()
                    .into(profile_pic);
        }
    }

    private class RegisterUser extends AsyncTask<String, Void, String> {
        private RegistrationModel registrationModel;

        public RegisterUser(RegistrationModel registrationModel) {
            this.registrationModel = registrationModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Please Wait..", Toast.LENGTH_SHORT).show();
            registerBtn.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;

            response = APICall.RegisterUser(registrationModel);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d(TAG, "onPostExecute: response " + response);
            if (response.contains("success")) {
                Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String token = jsonObject.getString("token");
                    editor.putString(PREF_TOKEN, token);
                    editor.apply();
                    startActivity(new Intent(context, MainScreen.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                registerBtn.setEnabled(true);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
