package com.example.feedbacksystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity {
    private TextView todayDate;
    Button  b1;
    EditText edt;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        b1 = findViewById(R.id.submitButton);
        todayDate = findViewById(R.id.todayDate);

        edt = findViewById(R.id.passwordField);
        setCurrentDate();


        b1.setOnClickListener(v -> {
            String password = edt.getText().toString();
            if(!password.isEmpty()){
                if(password.equals("Admin")){
                    i = new Intent(AdminActivity.this,AdminActivity2.class);
                    startActivity(i);
                }else{
                    new AlertDialog.Builder(AdminActivity.this).setTitle("Incorrect Password")
                            .setMessage("Wrong Passwrod")
                            .setPositiveButton("Ok",((dialog, which) -> {
                                dialog.dismiss();
                            })).show();

                }

            }
        });

    }

    private void setCurrentDate() {
        String currentDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        // Set the text to the TextView
        todayDate.setText("Today's Date: " + currentDate);
    }
}