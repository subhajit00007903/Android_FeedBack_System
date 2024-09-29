package com.example.feedbacksystem;

import android.annotation.SuppressLint;
import android.content.Intent; // Import Intent class
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentCSBSActivity extends AppCompatActivity {

    private LinearLayout teacherLayout;
    private DatabaseReference databaseCSBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_csbs);

        teacherLayout = findViewById(R.id.teacherLayout);
        Button finishButton = findViewById(R.id.finishButton); // Find the finish button

        databaseCSBS = FirebaseDatabase.getInstance().getReference("Departments/CSBS");

        // Fetch and display teacher data
        fetchTeachers();

        // Set up the finish button click listener
        finishButton.setOnClickListener(v -> {
            // Create an intent to navigate back to MainActivity
            Intent intent = new Intent(StudentCSBSActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Call finish() if you want to close this activity
        });
    }

    private void fetchTeachers() {
        databaseCSBS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherLayout.removeAllViews(); // Clear the layout before adding new data
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    Teacher teacher = teacherSnapshot.getValue(Teacher.class);
                    if (teacher != null) {
                        addTeacherToLayout(teacher, teacherSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentCSBSActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addTeacherToLayout(Teacher teacher, String teacherId) {
        View teacherView = getLayoutInflater().inflate(R.layout.item_teacher, null, false);

        TextView teacherNameTextView = teacherView.findViewById(R.id.teacherNameTextView);
        RatingBar ratingBar = teacherView.findViewById(R.id.ratingBar);
        Button submitRatingButton = teacherView.findViewById(R.id.submitRatingButton);

        teacherNameTextView.setText(teacher.getName());

        // Handle rating submission
        submitRatingButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            if (rating > 0) {
                // Store the rating in Firebase under a separate node (e.g., /CSBS/ratings)
                databaseCSBS.child(teacherId).child("ratings").push().setValue(rating)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(StudentCSBSActivity.this, "Rating submitted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentCSBSActivity.this, "Failed to submit rating", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(StudentCSBSActivity.this, "Please select a rating", Toast.LENGTH_SHORT).show();
            }
        });

        teacherLayout.addView(teacherView);
    }
}
