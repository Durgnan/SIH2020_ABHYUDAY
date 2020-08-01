package com.sih2020.abhyuday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.sih2020.abhyuday.MainActivity;
import com.sih2020.abhyuday.R;

public class UserProfileActivity extends AppCompatActivity {

    ImageView BackArrow;
    Button btnNext,btnSubmit;
    TextView tvUserDetails;
    TextInputEditText etAge,etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BackArrow = findViewById(R.id.BackArrow);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);

        tvUserDetails = findViewById(R.id.tvUserDetails);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age = etAge.getText().toString().trim();
                String weight = etWeight.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("Age",age);
                bundle.putString("Weight",weight);
                Intent intent = new Intent(UserProfileActivity.this, UserProfileActivity_2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void callNextUserDetailsScreen(View view){

        Intent intent = new Intent(getApplicationContext(),UserProfileActivity_2.class);

        //transition
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View,String>(BackArrow,"transition_back");
        pairs[1] = new Pair<View,String>(btnSubmit,"transition_submit");
        pairs[2] = new Pair<View,String>(tvUserDetails,"transition_user_details");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserProfileActivity.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
