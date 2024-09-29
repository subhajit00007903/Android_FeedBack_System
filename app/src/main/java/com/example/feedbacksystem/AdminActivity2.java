package com.example.feedbacksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity2 extends AppCompatActivity {

    Button addTeacherButton, deleteTeacherButton, csbsButton, itButton, cseButton, dsButton;
    Button csbsRatingButton, itRatingButton, cseRatingButton, dsRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        // Initialize buttons
        csbsButton = findViewById(R.id.csbsButton);
        itButton = findViewById(R.id.itButton);
        cseButton = findViewById(R.id.cseButton);
        dsButton = findViewById(R.id.dsButton);

        csbsRatingButton = findViewById(R.id.csbsRatingButton);
        itRatingButton = findViewById(R.id.itRatingButton);
        cseRatingButton = findViewById(R.id.cseRatingButton);
        dsRatingButton = findViewById(R.id.dsRatingButton);

        // Set OnClickListeners for department buttons
        csbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, CSBSActivity.class);
                startActivity(intent);
            }
        });

        itButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, ITActivity.class);
                startActivity(intent);
            }
        });

        cseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, CSEActivity.class);
                startActivity(intent);
            }
        });

        dsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, DSActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListeners for rating buttons
        csbsRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, AdminCSBSRating.class);
                startActivity(intent);
            }
        });

        itRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, AdminITRating.class);
                startActivity(intent);
            }
        });

        cseRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, AdminCSERating.class);
                startActivity(intent);
            }
        });

        dsRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity2.this, AdminDSRating.class);
                startActivity(intent);
            }
        });
    }
}
