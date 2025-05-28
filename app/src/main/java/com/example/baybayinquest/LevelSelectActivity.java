package com.example.baybayinquest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelSelectActivity extends AppCompatActivity {

    private GridLayout gridLevels;
    private int totalLevels = 15; // You can add more later
    private int unlockedLevel; // You can later load this from SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        gridLevels = findViewById(R.id.gridLevels);
        unlockedLevel = Prefs.getUnlockedLevel(this);

        Button btnBackToMenu = findViewById(R.id.btnBackToMenu);
        Button btnResetProgress = findViewById(R.id.btnResetProgress);

        btnBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(LevelSelectActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnResetProgress.setOnClickListener(v -> {
            new AlertDialog.Builder(LevelSelectActivity.this)
                    .setTitle("Reset Progress")
                    .setMessage("Are you sure you want to reset your progress? This will lock all levels again, making you start over from level 1.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Prefs.setUnlockedLevel(LevelSelectActivity.this, 1);
                        recreate(); // Refresh the activity to update UI
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        for (int i = 1; i <= totalLevels; i++) {
            Button levelBtn = new Button(this);
            levelBtn.setText("Level " + i);

            if (i <= unlockedLevel) {
                levelBtn.setEnabled(true);
                int finalI = i;
                levelBtn.setOnClickListener(view -> {
                    // Pass level number to game screen
                    Intent intent = new Intent(LevelSelectActivity.this, GameActivity.class);
                    intent.putExtra("level", finalI);
                    startActivity(intent);
                });
            } else {
                levelBtn.setEnabled(false);
            }

            gridLevels.addView(levelBtn);
        }
    }
}
