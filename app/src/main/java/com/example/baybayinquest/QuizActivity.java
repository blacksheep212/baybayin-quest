package com.example.baybayinquest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baybayinquest.database.BaybayinDatabaseHelper;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private QuizManager quizManager;
    private ScoreTracker scoreTracker;
    private TextView characterTextView, scoreTextView, feedbackTextView;
    private Button[] optionButtons;
    private QuizManager.QuizQuestion currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize components
        quizManager = new QuizManager(this);
        scoreTracker = new ScoreTracker();

        // Initialize views
        characterTextView = findViewById(R.id.characterTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        optionButtons = new Button[] {
                findViewById(R.id.option1Button),
                findViewById(R.id.option2Button),
                findViewById(R.id.option3Button),
                findViewById(R.id.option4Button)
        };

        // Load first question
        loadNewQuestion();

        // Set button listeners
        for (int i = 0; i < optionButtons.length; i++) {
            final int index = i;
            optionButtons[i].setOnClickListener(v -> checkAnswer(index));
        }
    }

    private void loadNewQuestion() {
        currentQuestion = quizManager.generateQuestion();
        characterTextView.setText(currentQuestion.getCorrectCharacter().getCharacter());
        List<BaybayinDatabaseHelper.BaybayinCharacter> options = currentQuestion.getOptions();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options.get(i).getSyllable());
        }

        feedbackTextView.setText("");
        scoreTextView.setText("Score: " + scoreTracker.getScore());
    }

    private void checkAnswer(int selectedIndex) {
        BaybayinDatabaseHelper.BaybayinCharacter selected = currentQuestion.getOptions().get(selectedIndex);
        boolean isCorrect = quizManager.isCorrectAnswer(selected, currentQuestion.getCorrectCharacter());

        if (isCorrect) {
            scoreTracker.recordCorrectAnswer();
            feedbackTextView.setText("Correct!");
        } else {
            scoreTracker.recordWrongAnswer();
            feedbackTextView.setText("Wrong! Correct: " +
                    currentQuestion.getCorrectCharacter().getSyllable());
        }

        // Load next question after a short delay
        characterTextView.postDelayed(this::loadNewQuestion, 1000);
    }
}