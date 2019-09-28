package com.android.vendy.screens.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.vendy.API_Handling.APICall;
import com.android.vendy.R;
import com.android.vendy.adapters.CategoriesListAdapter;
import com.android.vendy.models.RegistrationModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.android.vendy.Global.PREF_TOKEN;

/**
 * Created by the Sir Anku on 26-09-2019 at 02:49 AM .
 */
public class Fragment_SignUp extends Fragment {

    private static final String TAG = Fragment_SignUp.class.getSimpleName();
    EditText mobileNo_ET, fname_ET, lname_ET, pwd_ET, cpwd_ET;
    CardView registerBtn;
    SharedPreferences sharedPreferences;
    RecyclerView categoriesList;
    AppCompatCheckBox applyCheckBox;
    Context context;
    View view;

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
        categoriesList = view.findViewById(R.id.categoryGridlist);
        applyCheckBox = view.findViewById(R.id.applyCB);

        view.findViewById(R.id.select_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        applyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    categoriesList.setLayoutManager(new GridLayoutManager(context,4));
                    categoriesList.setAdapter(new CategoriesListAdapter(context));
                    categoriesList.setNestedScrollingEnabled(true);
                }else {
                    view.findViewById(R.id.selectCategoryTV).setVisibility(View.GONE);
                    categoriesList.setVisibility(View.GONE);

                }
            }
        });

        if (applyCheckBox.isChecked()){

        }else{

        }



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_ET.getText().toString().equals(cpwd_ET.getText().toString())) {
                    RegistrationModel registrationModel = new RegistrationModel(mobileNo_ET.getText().toString(),
                            pwd_ET.getText().toString(),
                            cpwd_ET.getText().toString());
                    new RegisterUser(registrationModel).execute();
                } else {
                    Toast.makeText(getContext(), "Password must match.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class RegisterUser extends AsyncTask<String, Void, String> {
        private RegistrationModel registrationModel;

        public RegisterUser(RegistrationModel registrationModel) {
            this.registrationModel = registrationModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

            if (response.contains("success")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String token = jsonObject.getString("token");
                    editor.putString(PREF_TOKEN, token);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
