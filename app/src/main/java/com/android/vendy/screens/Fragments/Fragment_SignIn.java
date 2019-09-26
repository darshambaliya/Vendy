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
import com.android.vendy.models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.vendy.Global.PREF_TOKEN;

/**
 * Created by the Sir Anku on 26-09-2019 at 01:23 AM .
 */
public class Fragment_SignIn extends Fragment {

    EditText mobileNo_ET, pwd_ET;
    CardView loginBtn;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                "myprefs", Context.MODE_PRIVATE);

        View view = getView();

        mobileNo_ET = view.findViewById(R.id.mob_noET);
        pwd_ET = view.findViewById(R.id.pwd_ET);
        loginBtn = view.findViewById(R.id.lognbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mobileNo_ET.getText().toString().isEmpty() && !pwd_ET.getText().toString().isEmpty()){
                    new GetToken(new LoginModel(mobileNo_ET.getText().toString(),pwd_ET.getText().toString())).execute();
                }
            }
        });
    }

    private class GetToken extends AsyncTask<String, Void, String>{
        LoginModel loginModel;

        public GetToken(LoginModel loginModel) {
            this.loginModel = loginModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;

            response = APICall.LoginUser(loginModel);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String token = jsonObject.getString("token");
                editor.putString(PREF_TOKEN, token);
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
