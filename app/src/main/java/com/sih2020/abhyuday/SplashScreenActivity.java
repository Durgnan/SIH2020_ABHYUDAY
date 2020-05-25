package com.sih2020.abhyuday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    Animation topAnimation,bottomAnimation;

    private static int SPLASH_SCREEN_TIME_OUT=3000;
    //After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        setContentView(R.layout.activity_splash_screen);
        final ImageView img = findViewById(R.id.logo);
        ImageView image=findViewById(R.id.logo);
        TextView info=findViewById(R.id.info);
        topAnimation=AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image.setAnimation(topAnimation);
        info.setAnimation(bottomAnimation);


//        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_screen_fade);
//
//        img.startAnimation(aniFade);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreenActivity.this,
                        OnBoardActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right);
                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}