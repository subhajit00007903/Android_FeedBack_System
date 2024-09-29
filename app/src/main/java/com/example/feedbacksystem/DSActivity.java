package com.example.feedbacksystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DSActivity extends AppCompatActivity {

    private Button buttonAddTeacher;
    private Button buttonRemoveTeacher;
    private Button buttonShowAll;
    private EditText editTextTeacher;
    private TextView textViewDetails; // TextView to show all teacher data
    private DatabaseReference databaseDSA;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csbsactivity);

        // Initialize Firebase Database reference for DSA department
        databaseDSA = FirebaseDatabase.getInstance().getReference("Departments/DSA");

        // Finding all views by their IDs
        buttonAddTeacher = findViewById(R.id.buttonAddTeacher);
        buttonRemoveTeacher = findViewById(R.id.buttonRemoveTeacher);
        buttonShowAll = findViewById(R.id.buttonShowAll);
        editTextTeacher = findViewById(R.id.editTextTeacher);
        textViewDetails = findViewById(R.id.textViewDetails); // Initialize TextView to display teacher details

        // Set onClickListeners for buttons
        buttonAddTeacher.setOnClickListener(v -> addTeacher());

        buttonRemoveTeacher.setOnClickListener(v -> removeTeacher());

        buttonShowAll.setOnClickListener(v -> showAllTeachers());
    }

    private void addTeacher() {
        String teacherName = editTextTeacher.getText().toString().trim();

        if (!TextUtils.isEmpty(teacherName)) {
            // Generate a unique ID for each teacher
            String id = databaseDSA.push().getKey();

            // Create a teacher object with the unique ID
            Teacher teacher = new Teacher(id, teacherName);

            // Add the teacher to the "DSA" node in Firebase
            if (id != null) {
                databaseDSA.child(id).setValue(teacher).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DSActivity.this, "Teacher added", Toast.LENGTH_SHORT).show();
                        editTextTeacher.setText("");  // Clear the EditText after adding
                    } else {
                        Toast.makeText(DSActivity.this, "Failed to add teacher", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Please enter a teacher name", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeTeacher() {
        String teacherName = editTextTeacher.getText().toString().trim();

        if (!TextUtils.isEmpty(teacherName)) {
            // Search through the DSA database to find the teacher by name
            databaseDSA.orderByChild("name").equalTo(teacherName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Loop through each child and remove it
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        Toast.makeText(DSActivity.this, "Teacher removed", Toast.LENGTH_SHORT).show();
                        editTextTeacher.setText("");  // Clear the EditText after removing
                    } else {
                        Toast.makeText(DSActivity.this, "Teacher not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DSActivity.this, "Failed to remove teacher", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter a teacher name", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllTeachers() {
        // Fetch all teachers from Firebase
        databaseDSA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder teacherDetails = new StringBuilder();

                if (dataSnapshot.exists()) {
                    // Loop through all children (teachers) and append their details to the StringBuilder
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Teacher teacher = snapshot.getValue(Teacher.class);
                        teacherDetails.append("ID: ").append(teacher.getId()).append("\n");
                        teacherDetails.append("Name: ").append(teacher.getName()).append("\n\n");
                    }
                } else {
                    teacherDetails.append("No teachers found.");
                }

                // Show the details in the TextView
                textViewDetails.setText(teacherDetails.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DSActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
