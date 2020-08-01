package com.sih2020.abhyuday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sih2020.abhyuday.MainActivity;
import com.sih2020.abhyuday.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity_2 extends AppCompatActivity {

    String age,weight;
    RadioGroup rgGender,rgBloodGroup;
    RadioButton rbGender,rbBloodGroup;
    ImageView BackArrow1;
    Button btnSubmit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_2);

        BackArrow1 = findViewById(R.id.BackArrow1);
        btnSubmit1 = findViewById(R.id.btnSubmit1);

        addListenerOnButton();

        BackArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             age = bundle.getString("Age");
             weight = bundle.getString("Weight");


        }
    }

    public void addListenerOnButton() {
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgBloodGroup = (RadioGroup) findViewById(R.id.rgBloodGroup);
        btnSubmit1 = (Button) findViewById(R.id.btnSubmit1);

        btnSubmit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// get selected radio button from radioGroup
                int selectedGender = rgGender.getCheckedRadioButtonId();
                int selectedBloodGroup =rgBloodGroup.getCheckedRadioButtonId();
// find the radiobutton by returned id
                rbGender = (RadioButton) findViewById(selectedGender);
                rbBloodGroup = (RadioButton) findViewById(selectedBloodGroup);
                Toast.makeText(UserProfileActivity_2.this, rbGender.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserProfileActivity_2.this, rbBloodGroup.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserProfileActivity_2.this, MainActivity.class);
                startActivity(intent);
                Log.e("SELECTED"," Age "+age+" Weight "+weight+" Gender "+rbGender.getText()+" Blood group "+rbBloodGroup.getText());
                //Code to Call Lambda

                //Get email

                SharedPreferences shared = getSharedPreferences("ABHYUDAY", MODE_PRIVATE);
                String email = (shared.getString("EMAIL", null));
                String uid = (shared.getString("UID", null));
                //If shared preference is not present
                if(email==null){
                    Intent intent2 = new Intent(UserProfileActivity_2.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();

                }
                Log.e("DEBUG EMAIL",email);
                String login_url = getResources().getString(R.string.GET_DATA);
                JSONObject jsonObject1=new JSONObject();
                JSONObject jsonObject2=new JSONObject();
                try {
                    jsonObject1.put("API_KEY",getResources().getString(R.string.API_KEY));
                    jsonObject2.put("email",email);
                    jsonObject2.put("age",age);
                    jsonObject2.put("weight",weight);
                    jsonObject2.put("sex",rbGender.getText());
                    jsonObject2.put("bgroup",rbBloodGroup.getText());
                    jsonObject2.put("authId",uid);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("MODE","ADD_MGENERAL");
                    jsonObject.put("headers",jsonObject1);
                    jsonObject.put("payload",jsonObject2);
                    Log.e("DEBUG",jsonObject.toString());
                    System.out.println("Check point 2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Request
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.POST, login_url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Check point 3.00");
                        Log.e("RESPONSE CODE",response.toString());

                        try {
                            Log.e("RESPONSE",response.toString());

                            if(response.getString("message").equals("SUCCESS"))
                            {
                                Toast.makeText(getApplicationContext(),"User Data sent Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserProfileActivity_2.this,MainActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Failed!! Contact Administrator !!!",Toast.LENGTH_LONG).show();
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




            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserProfileActivity_2.this, UserProfileActivity.class);
        startActivity(intent);
    }
}


