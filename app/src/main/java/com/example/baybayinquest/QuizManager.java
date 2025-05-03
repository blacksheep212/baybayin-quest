package com.example.baybayinquest;

import android.content.Context;

import com.example.baybayinquest.database.BaybayinDatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizManager {
    private BaybayinDatabaseHelper dbHelper;
    private Random random;

    public QuizManager(Context context) {
        dbHelper = new BaybayinDatabaseHelper(context);
        random = new Random();
    }

    // Represents a quiz question
    public static class QuizQuestion {
        private BaybayinDatabaseHelper.BaybayinCharacter correctCharacter;
        private List<BaybayinDatabaseHelper.BaybayinCharacter> options;

        public QuizQuestion(BaybayinDatabaseHelper.BaybayinCharacter correctCharacter, List<BaybayinDatabaseHelper.BaybayinCharacter> options) {
            this.correctCharacter = correctCharacter;
            this.options = options;
        }

        public BaybayinDatabaseHelper.BaybayinCharacter getCorrectCharacter() {
            return correctCharacter;
        }

        public List<BaybayinDatabaseHelper.BaybayinCharacter> getOptions() {
            return options;
        }
    }

    // Generate a new quiz question with 4 multiple-choice options
    public QuizQuestion generateQuestion() {
        BaybayinDatabaseHelper.BaybayinCharacter correct = dbHelper.getRandomCharacter();
        List<BaybayinDatabaseHelper.BaybayinCharacter> allCharacters = dbHelper.getAllCharacters();

        // Remove correct answer from the pool to avoid duplicates
        allCharacters.remove(correct);

        // Select 3 random incorrect options
        List<BaybayinDatabaseHelper.BaybayinCharacter> options = new ArrayList<>();
        options.add(correct);
        for (int i = 0; i < 3 && !allCharacters.isEmpty(); i++) {
            int index = random.nextInt(allCharacters.size());
            options.add(allCharacters.remove(index));
        }

        // Shuffle options
        Collections.shuffle(options);
        return new QuizQuestion(correct, options);
    }

    // Validate user's answer
    public boolean isCorrectAnswer(BaybayinDatabaseHelper.BaybayinCharacter selected, BaybayinDatabaseHelper.BaybayinCharacter correct) {
        return selected.getId() == correct.getId();
    }
}