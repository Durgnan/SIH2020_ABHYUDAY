package com.sih2020.abhyuday.Ambulance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.sih2020.abhyuday.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    MaterialButton btnRegister;
    EditText etEmail, etPassword;
    Button btnSignIn;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        pref = getSharedPreferences("ABHYUDAY", MODE_PRIVATE);
        editor = pref.edit();

        btnRegister = findViewById(R.id.tv_register);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty()) {
                etEmail.setError("Email field is empty");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password field is empty");
                return;
            }
            String url = getResources().getString(R.string.GET_DATA);
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject1.put("API_KEY", getResources().getString(R.string.API_KEY));
                jsonObject2.put("email", email);
                jsonObject2.put("pass", password);

                jsonObject.put("MODE", "AUTH_AMBULANCE");
                jsonObject.put("headers", jsonObject1);
                jsonObject.put("payload", jsonObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("message").equals("SUCCESS")) {
                            editor.putString("EMAIL", email);
                            editor.putString("USER_TYPE", "AMBULANCE");
                            editor.apply();
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else if (response.getString("message").equals("ACCESS_DENIED")) {
                            Toast.makeText(getApplicationContext(), "Login Failed!!\nWrong Email or Password", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            requestQueue.add(objectRequest);
        });
    }
}