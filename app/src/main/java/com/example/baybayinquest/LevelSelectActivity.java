package com.example.baybayinquest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelSelectActivity extends AppCompatActivity {

    private GridLayout gridLevels;
    private int totalLevels = 6; // You can add more later
    private int unlockedLevel; // You can later load this from SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        gridLevels = findViewById(R.id.gridLevels);

        unlockedLevel = Prefs.getUnlockedLevel(this);


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
