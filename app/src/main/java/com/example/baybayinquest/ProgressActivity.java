package com.example.baybayinquest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {

    private TextView xpToday, xpThisWeek, masteredCharacters;
    private LinearLayout barChart;
    private Button viewAchievementsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress); // change to your actual layout name

        xpToday = findViewById(R.id.xpToday);
        xpThisWeek = findViewById(R.id.xpThisWeek);
        masteredCharacters = findViewById(R.id.masteredCharacters);
        barChart = findViewById(R.id.barChart);
        viewAchievementsButton = findViewById(R.id.viewAchievementsButton);

        // Simulated data
        int todayXP = 9;
        int thisWeekXP = 27;
        int masteredBaybayin = 11;
        int[] weeklyXP = {20, 15, 25, 10, 18}; // W1 to W5

        // Set text values
        xpToday.setText(todayXP + " XP");
        xpThisWeek.setText(thisWeekXP + " XP");
        masteredCharacters.setText("Baybayin Mastered: " + masteredBaybayin);

        // Generate bar chart
        drawBarChart(weeklyXP);

        // Button logic
        viewAchievementsButton.setOnClickListener(v -> {
            // You can launch a new activity or display a dialog here
            // Example:
            // startActivity(new Intent(ProgressActivity.this, AchievementsActivity.class));
        });
    }

    private void drawBarChart(int[] weeklyXP) {
        int maxXP = getMax(weeklyXP);

        for (int xp : weeklyXP) {
            View bar = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    (int) ((xp / (float) maxXP) * 200)); // normalize to barChart height
            params.weight = 1;
            params.setMargins(8, 0, 8, 0);
            bar.setLayoutParams(params);
            bar.setBackgroundColor(Color.parseColor("#FF6200EE")); // purple
            barChart.addView(bar);
        }
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) max = num;
        }
        return max;
    }
}
