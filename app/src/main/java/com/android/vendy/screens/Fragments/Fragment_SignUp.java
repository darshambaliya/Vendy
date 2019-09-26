package com.android.vendy.screens.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.vendy.API_Handling.APICall;
import com.android.vendy.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "myprefs", Context.MODE_PRIVATE);

        View view = getView();


        mobileNo_ET = view.findViewById(R.id.mob_noET);
        fname_ET = view.findViewById(R.id.fname_ET);
        lname_ET = view.findViewById(R.id.lname_ET);
        pwd_ET = view.findViewById(R.id.pwd_ET);
        cpwd_ET = view.findViewById(R.id.cpwd_ET);
        registerBtn = view.findViewById(R.id.regBtn);

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
                Toast.makeText(getContext(), "Wohoo", Toast.LENGTH_SHORT).show();
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
