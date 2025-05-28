package com.example.baybayinquest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class LevelCompleteActivity extends AppCompatActivity {

    TextView tvLevelComplete, tvScore, tvMessage;
    Button btnBackToLevels, btnRetry, btnNextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);

        tvLevelComplete = findViewById(R.id.tvLevelComplete);
        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        btnBackToLevels = findViewById(R.id.btnBackToLevels);
        btnRetry = findViewById(R.id.btnRetry);
        btnNextLevel = findViewById(R.id.btnNextLevel);

        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 10);
        int level = getIntent().getIntExtra("level", 1);

        tvScore.setText("You got " + correctAnswers + " out of " + totalQuestions + " correct.");

        if (correctAnswers >= 7) {
            tvLevelComplete.setText("🎉 Level " + level + " Complete!");
            tvLevelComplete.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            tvMessage.setText("Great job! You're getting better at reading Baybayin.\n\nReady for the next challenge?\n\n");
            tvMessage.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            btnNextLevel.setVisibility(Button.VISIBLE);
        } else {
            tvLevelComplete.setText("❌ Level " + level + " Failed");
            tvLevelComplete.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvMessage.setText("You need at least 7 correct answers to pass.\n\nNo worries, try again and you'll get there!\n\n");
            tvMessage.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            btnNextLevel.setVisibility(Button.GONE);
        }

        // Back to Level Select
        btnBackToLevels.setOnClickListener(v -> {
            Intent intent = new Intent(LevelCompleteActivity.this, LevelSelectActivity.class);
            startActivity(intent);
            finish();
        });

        // Retry same level
        btnRetry.setOnClickListener(v -> {
            Intent intent = new Intent(LevelCompleteActivity.this, GameActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
            finish();
        });

        // Proceed to next level (only works if passed)
        btnNextLevel.setOnClickListener(v -> {
            Intent intent = new Intent(LevelCompleteActivity.this, GameActivity.class);
            intent.putExtra("level", level + 1);
            startActivity(intent);
            finish();
        });
    }
}
