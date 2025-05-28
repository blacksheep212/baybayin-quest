package com.example.baybayinquest;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    TextView tvLevel, tvBaybayinSymbol, tvFeedback;
    RadioGroup radioGroup;
    Button btnSubmit, btnNext;

    String correctAnswer = "ba"; // For now, static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvLevel = findViewById(R.id.tvLevel);
        tvBaybayinSymbol = findViewById(R.id.tvBaybayinSymbol);
        radioGroup = findViewById(R.id.radioGroupChoices);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);
        tvFeedback = findViewById(R.id.tvFeedback);

        // Optional: Get level number from previous activity
        int level = getIntent().getIntExtra("level", 1);
        tvLevel.setText("Level " + level);

        btnSubmit.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = findViewById(selectedId);
            String answer = selected.getText().toString();

            if (answer.equals(correctAnswer)) {
                tvFeedback.setText("Correct!");
                tvFeedback.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvFeedback.setText("Incorrect!");
                tvFeedback.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            tvFeedback.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnSubmit.setEnabled(false);
        });

        btnNext.setOnClickListener(v -> {
            int currentLevel = getIntent().getIntExtra("level", 1);
            int savedLevel = Prefs.getUnlockedLevel(this);

            // Unlock next level if it's not yet unlocked
            if (currentLevel >= savedLevel) {
                Prefs.setUnlockedLevel(this, currentLevel + 1);
            }

            finish(); // Go back to LevelSelectActivity
        });
    }
}
