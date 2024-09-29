package com.example.feedbacksystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Landing_page extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        ImageView logoImageView = findViewById(R.id.logoImageView);


        Animation popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.popup_animation);


        logoImageView.setVisibility(ImageView.VISIBLE);
        logoImageView.startAnimation(popUpAnimation);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Landing_page.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
