package com.example.feedbacksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class student_department_selection extends AppCompatActivity {

    private Button buttonCSBS;
    private Button buttonCSE;
    private Button buttonIT;
    private Button buttonDSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_department_selection);



        // Initialize buttons by finding them by ID
        buttonCSBS = findViewById(R.id.buttonCSBS);
        buttonCSE = findViewById(R.id.buttonCSE);
        buttonIT = findViewById(R.id.buttonIT);
        buttonDSA = findViewById(R.id.buttonDSA);

        // Set OnClickListeners for each button to move to the respective department activity
        buttonCSBS.setOnClickListener(v -> openDepartmentActivity(StudentCSBSActivity.class));
        buttonCSE.setOnClickListener(v -> openDepartmentActivity(StudentCSEActivity.class));
        buttonIT.setOnClickListener(v -> openDepartmentActivity(StudentITActivity.class));
        buttonDSA.setOnClickListener(v -> openDepartmentActivity(StudentDSAActivity.class));
    }

    // Method to open the respective department activity
    private void openDepartmentActivity(Class<?> departmentActivity) {
        Intent intent = new Intent(student_department_selection.this, departmentActivity);
        startActivity(intent);
    }
}
