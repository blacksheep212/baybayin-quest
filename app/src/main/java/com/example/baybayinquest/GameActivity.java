package com.example.baybayinquest;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    private int correctAnswers = 0;
    private static final int PASSING_SCORE = 7;


    TextView tvLevel, tvBaybayinSymbol, tvFeedback;
    RadioGroup radioGroup;
    Button btnSubmit, btnNext;
    TextView tvQuestionCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // UI refs
        tvLevel = findViewById(R.id.tvLevel);
        tvBaybayinSymbol = findViewById(R.id.tvBaybayinSymbol);
        tvFeedback = findViewById(R.id.tvFeedback);
        radioGroup = findViewById(R.id.radioGroupChoices);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);
        tvQuestionCount = findViewById(R.id.tvQuestionCount);


        // Get current level
        int level = getIntent().getIntExtra("level", 1);
        tvLevel.setText("Level " + level);

        // Load questions
        questions = getQuestionsForLevel(level);
        loadQuestion(currentQuestionIndex);

        // Submit answer
        btnSubmit.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = findViewById(selectedId);
            String answer = selected.getText().toString();
            Question current = questions.get(currentQuestionIndex);

            if (answer.equals(current.getCorrectAnswer())) {
                correctAnswers++;
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


        // Next question or finish level
        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;

            if (currentQuestionIndex < questions.size()) {
                loadQuestion(currentQuestionIndex);
            } else {
                // Level finished - check score
                if (correctAnswers >= PASSING_SCORE) {
                    int currentLevel = getIntent().getIntExtra("level", 1);
                    int savedLevel = Prefs.getUnlockedLevel(this);

                    if (currentLevel >= savedLevel) {
                        Prefs.setUnlockedLevel(this, currentLevel + 1);
                    }

                    Toast.makeText(this, "Level Passed! ✅", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Level Failed ❌\nScore: " + correctAnswers + "/" + questions.size(), Toast.LENGTH_LONG).show();
                }

                finish(); // Return to LevelSelectActivity
            }
        });
    }

    private void loadQuestion(int index) {
        Question q = questions.get(index);
        tvBaybayinSymbol.setText(q.getSymbol());

        // Update question count (e.g., "Question 3/10")
        tvQuestionCount.setText("Question " + (index + 1) + "/" + questions.size());

        // Update choices
        RadioButton[] options = {
                findViewById(R.id.choice1),
                findViewById(R.id.choice2),
                findViewById(R.id.choice3),
                findViewById(R.id.choice4)
        };

        for (int i = 0; i < options.length; i++) {
            options[i].setText(q.getChoices()[i]);
        }

        // Reset selection and feedback
        radioGroup.clearCheck();
        tvFeedback.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnSubmit.setEnabled(true);
    }


    private List<Question> getQuestionsForLevel(int level) {
        List<Question> list = new ArrayList<>();

        if (level == 1) {
            list.add(new Question("ᜊ", new String[]{"ba", "ka", "la", "ma"}, "ba"));
            list.add(new Question("ᜃ", new String[]{"ga", "ta", "ka", "na"}, "ka"));
            list.add(new Question("ᜇ", new String[]{"da", "sa", "ba", "ga"}, "da"));
            list.add(new Question("ᜐ", new String[]{"sa", "ta", "na", "ka"}, "sa"));
            list.add(new Question("ᜁ", new String[]{"a", "i", "e", "o"}, "i"));
            list.add(new Question("ᜀ", new String[]{"a", "e", "u", "i"}, "a"));
            list.add(new Question("ᜉ", new String[]{"pa", "ba", "ta", "ka"}, "pa"));
            list.add(new Question("ᜎ", new String[]{"la", "ka", "ma", "na"}, "la"));
            list.add(new Question("ᜅ", new String[]{"nga", "ga", "na", "ka"}, "nga"));
            list.add(new Question("ᜇ᜔", new String[]{"di", "da", "du", "de"}, "d")); // simplified form
        } else if (level == 2) {
            list.add(new Question("ᜂ", new String[]{"u", "a", "i", "e"}, "u"));
            list.add(new Question("ᜎ", new String[]{"la", "ka", "ma", "sa"}, "la"));
            list.add(new Question("ᜈ", new String[]{"na", "nga", "ma", "la"}, "na"));
            list.add(new Question("ᜋ", new String[]{"ma", "la", "na", "ta"}, "ma"));
            list.add(new Question("ᜅ", new String[]{"nga", "na", "ka", "ga"}, "nga"));
            list.add(new Question("ᜆ", new String[]{"ta", "da", "sa", "ka"}, "ta"));
            list.add(new Question("ᜏ", new String[]{"wa", "ba", "ma", "sa"}, "wa"));
            list.add(new Question("ᜌ", new String[]{"ya", "na", "la", "ga"}, "ya"));
            list.add(new Question("ᜂ", new String[]{"u", "o", "e", "i"}, "u"));
            list.add(new Question("ᜊ᜔", new String[]{"b", "ba", "bu", "bo"}, "b")); // simplified form
        }

        return list;
    }

}
