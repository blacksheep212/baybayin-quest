package com.example.baybayinquest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baybayinquest.database.BaybayinDatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set your home layout XML here:
        setContentView(R.layout.activity_main);  // <-- ensure this matches your actual XML file name

        // Apply window insets padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize your database helper
        BaybayinDatabaseHelper dbHelper = new BaybayinDatabaseHelper(this);

        // Test retrieving all characters (for debug purposes)
        List<BaybayinDatabaseHelper.BaybayinCharacter> characters = dbHelper.getAllCharacters();
        for (BaybayinDatabaseHelper.BaybayinCharacter character : characters) {
            System.out.println("Character: " + character.getCharacter() + ", Syllable: " + character.getSyllable());
        }

        // Test retrieving a random character
        BaybayinDatabaseHelper.BaybayinCharacter randomChar = dbHelper.getRandomCharacter();
        if (randomChar != null) {
            System.out.println("Random Character: " + randomChar.getCharacter());
        }
    }
}
