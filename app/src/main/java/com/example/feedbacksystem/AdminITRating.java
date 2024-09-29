package com.example.feedbacksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminITRating extends AppCompatActivity {

    private LinearLayout ratingsLayout;
    private DatabaseReference databaseIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_itrating);

        // Initialize the ratings layout and database reference
        ratingsLayout = findViewById(R.id.ratingsLayout);
        databaseIT = FirebaseDatabase.getInstance().getReference("Departments/IT");

        // Set up window insets
        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v->shareRatings());

        // Fetch and display ratings
        fetchRatings();
    }

    private void shareRatings() {
        StringBuilder sb = new StringBuilder("Teacher Ratings");
        Intent i = new Intent(Intent.ACTION_SEND);

        for(int j =0;j<ratingsLayout.getChildCount();j++){
            View v = ratingsLayout.getChildAt(j);

        }
        i.setType("text/plain");
        i.putExtra(i.EXTRA_TEXT,sb.toString());
        startActivity(Intent.createChooser(i,"Share Rating Via"));
    }

    private void fetchRatings() {
        databaseIT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingsLayout.removeAllViews(); // Clear previous data
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()) {
                    String teacherName = teacherSnapshot.child("name").getValue(String.class);
                    DataSnapshot ratingsSnapshot = teacherSnapshot.child("ratings");

                    if (ratingsSnapshot.exists()) {
                        StringBuilder ratingData = new StringBuilder(teacherName + ": ");
                        for (DataSnapshot rating : ratingsSnapshot.getChildren()) {
                            ratingData.append(rating.getValue(Float.class)).append(", ");
                        }
                        // Remove last comma and space
                        if (ratingData.length() > 2) {
                            ratingData.setLength(ratingData.length() - 2);
                        }
                        addRatingToLayout(ratingData.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminITRating.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRatingToLayout(String ratingData) {
        TextView ratingTextView = new TextView(this);
        ratingTextView.setText(ratingData);
        ratingsLayout.addView(ratingTextView);
    }
}
