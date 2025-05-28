package com.example.baybayinquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private ProgressBar xpProgressBar;
    private TextView xpText, streakText;
    private Button startQuizButton, reviewSignsButton, learnNewSignsButton;
    private ImageButton navHome, navProgress, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        xpProgressBar = findViewById(R.id.xpProgressBar);
        xpText = findViewById(R.id.xpText);
        streakText = findViewById(R.id.streakText);
        startQuizButton = findViewById(R.id.startQuizButton);
        reviewSignsButton = findViewById(R.id.reviewSignsButton);
        learnNewSignsButton = findViewById(R.id.learnNewSignsButton);
        navHome = findViewById(R.id.navHome);
        navProgress = findViewById(R.id.navProgress);
        navProfile = findViewById(R.id.navProfile);

        // Dummy XP/Streak update logic (optional)
        xpProgressBar.setProgress(30); // dynamically update XP progress
        xpText.setText("30 XP");
        streakText.setText("2 Days Streak");

        // Button click listeners
        startQuizButton.setOnClickListener(v -> {
            // startActivity(new Intent(this, QuizActivity.class));
            Toast.makeText(this, "Start Quiz clicked", Toast.LENGTH_SHORT).show();
        });

        reviewSignsButton.setOnClickListener(v -> {
            // startActivity(new Intent(this, ReviewSignsActivity.class));
            Toast.makeText(this, "Review Signs clicked", Toast.LENGTH_SHORT).show();
        });

        learnNewSignsButton.setOnClickListener(v -> {
            // startActivity(new Intent(this, LearnNewSignsActivity.class));
            Toast.makeText(this, "Learn New Signs clicked", Toast.LENGTH_SHORT).show();
        });

        // Bottom Nav listeners (placeholder)
        navHome.setOnClickListener(v -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show());
        navProgress.setOnClickListener(v -> Toast.makeText(this, "Progress", Toast.LENGTH_SHORT).show());
        navProfile.setOnClickListener(v -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show());
    }
}
