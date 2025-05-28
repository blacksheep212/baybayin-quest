package com.example.baybayinquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStartGame, btnViewStats, btnGlossary, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnViewStats = findViewById(R.id.btnViewStats);
        btnGlossary = findViewById(R.id.btnGlossary);
        btnExit = findViewById(R.id.btnExit);

        btnStartGame.setOnClickListener(view -> {
            // Replace with LevelSelectActivity when created
            startActivity(new Intent(MainActivity.this, LevelSelectActivity.class));
        });

        btnViewStats.setOnClickListener(view -> {
            // Replace with StatsActivity when created
            // startActivity(new Intent(MainActivity.this, StatsActivity.class));
        });

        btnGlossary.setOnClickListener(view -> {
            // Replace with GlossaryActivity when created
            startActivity(new Intent(MainActivity.this, GlossaryActivity.class));
        });

        btnExit.setOnClickListener(view -> finishAffinity()); // Closes the app
    }
}