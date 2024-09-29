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

public class AdminCSERating extends AppCompatActivity {

    private LinearLayout ratingsLayout;
    private DatabaseReference databaseCSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cserating);

        // Initialize the ratings layout and database reference
        ratingsLayout = findViewById(R.id.ratingsLayout);
        databaseCSE = FirebaseDatabase.getInstance().getReference("Departments/CSE");

        Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> shareRatings());

        // Fetch and display ratings
        fetchRatings();
    }



    private void fetchRatings() {
        databaseCSE.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(AdminCSERating.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRatingToLayout(String ratingData) {
        TextView ratingTextView = new TextView(this);
        ratingTextView.setText(ratingData);
        ratingsLayout.addView(ratingTextView);
    }

    private void shareRatings() {
        StringBuilder ratingsToShare = new StringBuilder("Teacher Ratings:\n");
        for (int i = 0; i < ratingsLayout.getChildCount(); i++) {
            View view = ratingsLayout.getChildAt(i);
            if (view instanceof TextView) {
                ratingsToShare.append(((TextView) view).getText()).append("\n");
            }
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ratingsToShare.toString());
        startActivity(Intent.createChooser(shareIntent, "Share Ratings via"));

    }
}
