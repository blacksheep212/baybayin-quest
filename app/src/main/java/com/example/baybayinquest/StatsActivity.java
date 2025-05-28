package com.example.baybayinquest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StatsActivity extends AppCompatActivity {

    private TextView tvLevelsCompleted, tvQuestionsAnswered, tvCorrect, tvIncorrect, tvStreak, tvAccuracy;
    private SharedPreferences prefs;
    private TextView tvCurrentStreak, tvMostRepeated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        prefs = getSharedPreferences("GameStats", MODE_PRIVATE);

        tvLevelsCompleted = findViewById(R.id.tvLevelsCompleted);
        tvQuestionsAnswered = findViewById(R.id.tvQuestionsAnswered);
        tvCorrect = findViewById(R.id.tvCorrectAnswers);
        tvIncorrect = findViewById(R.id.tvIncorrectAnswers);
        tvStreak = findViewById(R.id.tvBestStreak);
        tvAccuracy = findViewById(R.id.tvAccuracyRate);
        Button btnBack = findViewById(R.id.btnBackToMenu);
        tvCurrentStreak = findViewById(R.id.tvCurrentStreak);
        tvMostRepeated = findViewById(R.id.tvMostRepeated);


        // Load stats
        int levelsCompleted = prefs.getInt("levelsCompleted", 0);
        int totalAnswered = prefs.getInt("totalAnswered", 0);
        int correctAnswers = prefs.getInt("correctAnswers", 0);
        int incorrectAnswers = prefs.getInt("incorrectAnswers", 0);
        int bestStreak = prefs.getInt("bestStreak", 0);
        int currentStreak = prefs.getInt("currentStreak", 0);
        int totalScore = prefs.getInt("totalScore", 0);
        int levelsPlayed = prefs.getInt("levelsPlayed", 0);

        double accuracy = totalAnswered > 0 ? (correctAnswers * 100.0) / totalAnswered : 0;

        int mostRepeatedLevel = -1;
        int maxCount = -1;
        for (int i = 1; i <= 15; i++) { // assuming 15 levels
            int count = prefs.getInt("levelCount_" + i, 0);
            if (count > maxCount) {
                maxCount = count;
                mostRepeatedLevel = i;
            }
        }

        // Display
        tvLevelsCompleted.setText("Levels Completed: " + levelsCompleted);
        tvQuestionsAnswered.setText("Questions Answered: " + totalAnswered);
        tvCorrect.setText("Correct Answers: " + correctAnswers);
        tvIncorrect.setText("Incorrect Answers: " + incorrectAnswers);
        tvStreak.setText("Best Streak: " + bestStreak);
        tvAccuracy.setText(String.format("Accuracy Rate: %.1f%%", accuracy));
        tvCurrentStreak.setText("Current Streak: " + currentStreak);
        tvMostRepeated.setText("Most Repeated Level: Level " + (mostRepeatedLevel == -1 ? "N/A" : mostRepeatedLevel));

        btnBack.setOnClickListener(v -> finish());
    }
}
