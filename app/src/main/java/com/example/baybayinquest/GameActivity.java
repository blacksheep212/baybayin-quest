package com.example.baybayinquest;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
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
    private int score = 0;

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
                score++;
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

                Intent intent = new Intent(GameActivity.this, LevelCompleteActivity.class);
                intent.putExtra("correctAnswers", score);
                intent.putExtra("totalQuestions", questions.size());
                intent.putExtra("level", level);
                startActivity(intent);
                finish();

            }
        });

        Button btnQuit = findViewById(R.id.btnQuit);

        btnQuit.setOnClickListener(v -> {
            new AlertDialog.Builder(GameActivity.this)
                    .setTitle("Quit Level")
                    .setMessage("Are you sure you want to quit this level? Your progress will be lost.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(GameActivity.this, LevelSelectActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // ensure user can't press back to return
                    })
                    .setNegativeButton("No", null)
                    .show();
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
            list.add(new Question("ᜇ᜔", new String[]{"di", "da", "du", "de"}, "d")); // simplified

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
            list.add(new Question("ᜊ᜔", new String[]{"b", "ba", "bu", "bo"}, "b")); // simplified

        } else if (level == 3) {
            list.add(new Question("ᜐ", new String[]{"sa", "na", "ka", "ga"}, "sa"));
            list.add(new Question("ᜉ", new String[]{"pa", "ta", "ba", "la"}, "pa"));
            list.add(new Question("ᜌ", new String[]{"ya", "la", "na", "ga"}, "ya"));
            list.add(new Question("ᜎ", new String[]{"la", "ta", "ma", "na"}, "la"));
            list.add(new Question("ᜇ", new String[]{"da", "ba", "ga", "ta"}, "da"));
            list.add(new Question("ᜏ", new String[]{"wa", "ba", "sa", "ka"}, "wa"));
            list.add(new Question("ᜈ", new String[]{"ra", "la", "na", "ma"}, "na"));
            list.add(new Question("ᜆ", new String[]{"ta", "sa", "ka", "pa"}, "ta"));
            list.add(new Question("ᜃ᜔", new String[]{"k", "ka", "ku", "ko"}, "k")); // simplified
            list.add(new Question("ᜁᜓ", new String[]{"io", "iu", "eo", "eu"}, "iu")); // compound

        } else if (level == 4) {
            list.add(new Question("ᜊᜊ", new String[]{"baba", "baka", "babae", "bata"}, "baba"));
            list.add(new Question("ᜃᜋ", new String[]{"kama", "kamao", "kamo", "kami"}, "kama"));
            list.add(new Question("ᜈᜈ", new String[]{"nana", "nene", "nono", "nani"}, "nana"));
            list.add(new Question("ᜋᜈ", new String[]{"mana", "mama", "nana", "nama"}, "mana"));
            list.add(new Question("ᜊᜌ", new String[]{"baya", "baba", "bata", "baka"}, "baya"));
            list.add(new Question("ᜐᜐ", new String[]{"sasa", "sasae", "saka", "sana"}, "sasa"));
            list.add(new Question("ᜊᜋ", new String[]{"bama", "bana", "baka", "bata"}, "bama"));
            list.add(new Question("ᜈᜌ", new String[]{"naya", "nana", "kaya", "maya"}, "naya"));
            list.add(new Question("ᜋᜌ", new String[]{"maya", "mayo", "mama", "naya"}, "maya"));
            list.add(new Question("ᜈᜇ", new String[]{"nada", "nana", "naga", "dada"}, "nada"));

        } else if (level == 5) {
            list.add(new Question("ᜇᜒᜆᜓ", new String[]{"dito", "datu", "duto", "dito"}, "dito"));
            list.add(new Question("ᜋᜊᜓᜑᜌ", new String[]{"mabuhay", "mapuhay", "mabuhai", "mabohay"}, "mabuhay"));
            list.add(new Question("ᜐᜒᜈ᜔ᜆ᜔", new String[]{"sint", "sinat", "sinad", "sint"}, "sint")); // tricky cluster
            list.add(new Question("ᜊᜒᜈ᜔ᜐᜒ", new String[]{"binisi", "binasi", "binisi", "binesi"}, "binisi"));
            list.add(new Question("ᜈᜆᜓ", new String[]{"natu", "nato", "natoo", "natu"}, "natu"));
            list.add(new Question("ᜆᜎᜓᜈ᜔", new String[]{"talon", "talong", "tulan", "tulon"}, "talon"));
            list.add(new Question("ᜐᜒᜈ᜔ᜆᜓ", new String[]{"sinto", "santa", "sinta", "sinta"}, "sinta"));
            list.add(new Question("ᜈᜋ᜔ᜊᜒ", new String[]{"nambi", "nambe", "nam-bi", "nam-bi"}, "nambi"));
            list.add(new Question("ᜋᜒᜐ᜔ᜈᜒ", new String[]{"misni", "misne", "misni", "misni"}, "misni"));
            list.add(new Question("ᜐᜌ᜔ᜈᜓ", new String[]{"saynu", "sanyo", "sanyu", "sanyo"}, "sanyo"));
        }  else if (level == 6) {
            list.add(new Question("ᜆᜒᜎᜒᜈ", new String[]{"tilin", "tilan", "talin", "tulin"}, "tilin"));
            list.add(new Question("ᜋᜎᜓᜈ", new String[]{"malon", "malun", "malon", "malun"}, "malon"));
            list.add(new Question("ᜊᜓᜎᜓᜃ", new String[]{"bulok", "buluk", "balok", "bolok"}, "bulok"));
            list.add(new Question("ᜆᜓᜃᜒᜈ", new String[]{"tukin", "takun", "tokin", "tukin"}, "tukin"));
            list.add(new Question("ᜐᜎᜑᜌ", new String[]{"salaya", "saliya", "salhya", "salaya"}, "salaya"));
            list.add(new Question("ᜉ᜔ᜐ᜔ᜎ᜔", new String[]{"psl", "pasal", "psal", "pasil"}, "psl")); // challenge
            list.add(new Question("ᜆᜎ᜔ᜃᜒ", new String[]{"talgi", "talki", "talgi", "tulki"}, "talgi"));
            list.add(new Question("ᜉᜓᜄᜒᜈ", new String[]{"pugin", "pugon", "pagin", "puging"}, "pugin"));
            list.add(new Question("ᜈᜓᜈᜒᜐ", new String[]{"nunis", "nunes", "ninis", "nunes"}, "nunis"));
            list.add(new Question("ᜀᜐ᜔ᜎᜒ", new String[]{"asli", "asle", "asli", "asli"}, "asli"));
        }

        else if (level == 7) {
            list.add(new Question("ᜆ᜔ᜃᜒᜈ᜔ᜆᜒ", new String[]{"tikinti", "takinte", "takinte", "takinti"}, "takinti"));
            list.add(new Question("ᜋᜒᜎᜓᜐ᜔ᜈᜒ", new String[]{"milusni", "milusne", "milisni", "milusni"}, "milusni"));
            list.add(new Question("ᜐᜒᜇᜒᜎ", new String[]{"sidil", "sidel", "sidel", "sidil"}, "sidil"));
            list.add(new Question("ᜊᜒᜑᜒᜃ", new String[]{"bihik", "bihig", "bihek", "bihik"}, "bihik"));
            list.add(new Question("ᜉ᜔ᜎᜑᜒᜐ", new String[]{"plahis", "palahis", "palhis", "plhis"}, "palhis"));
            list.add(new Question("ᜃᜓᜈᜒᜆ᜔", new String[]{"kunit", "kunet", "konit", "kunit"}, "kunit"));
            list.add(new Question("ᜐᜒᜌᜒᜃ", new String[]{"siyik", "siyeq", "siyek", "siyik"}, "siyik"));
            list.add(new Question("ᜈ᜔ᜇᜒᜐ᜔", new String[]{"ndis", "ndes", "ndis", "ndis"}, "ndis")); // trick
            list.add(new Question("ᜂᜆ᜔ᜈ᜔ᜐ", new String[]{"utns", "otnas", "utnas", "utans"}, "utnas"));
            list.add(new Question("ᜉᜒᜆᜓᜎᜒ", new String[]{"pituli", "pitole", "petuli", "pituli"}, "pituli"));
        }

        else if (level == 8) {
            list.add(new Question("ᜋᜊᜓᜑᜌ ᜀᜈ᜔", new String[]{"Mabuhay an", "Mabuhay ka", "Mabuhay na", "Mabuhay ang"}, "Mabuhay ang"));
            list.add(new Question("ᜐᜓᜈᜒᜇ᜔ ᜋᜊᜓᜑᜌ", new String[]{"Sundin Mabuhay", "Sunid Mabuhay", "Sundin Mabuhay", "Suno Mabuhay"}, "Sundin Mabuhay"));
            list.add(new Question("ᜀᜈ᜔ᜆ᜔ᜇ᜔ ᜐᜒᜈᜓ", new String[]{"Antad sino", "Anad sino", "Andad sino", "Antad sino"}, "Antad sino"));
            list.add(new Question("ᜆᜓᜎᜒᜃ ᜃᜓᜃ", new String[]{"Tulik kuk", "Tolik kok", "Tolik kuk", "Tulik kok"}, "Tulik kuk"));
            list.add(new Question("ᜇᜓᜃᜓᜈᜒ ᜀᜎᜓ", new String[]{"Dukuni alu", "Dukune alo", "Dukuni alu", "Dukune alu"}, "Dukuni alu"));
            list.add(new Question("ᜆᜎ᜔ᜃ ᜀᜐᜓ", new String[]{"talka aso", "talka asu", "talka aso", "talca asu"}, "talka aso"));
            list.add(new Question("ᜀᜃᜒᜈᜒᜆ᜔ ᜐᜒ", new String[]{"akinite si", "akinet si", "akinite si", "akineti si"}, "akinite si"));
            list.add(new Question("ᜐᜒᜆᜓᜎᜒᜈ᜔", new String[]{"situlin", "sitolen", "situlin", "sitolun"}, "situlin"));
            list.add(new Question("ᜋᜒᜎᜓᜐ᜔ ᜐᜌᜒᜈ᜔", new String[]{"milus sayin", "milus sayan", "milus sayin", "milis sayin"}, "milus sayin"));
            list.add(new Question("ᜆᜓᜇ᜔ ᜈᜋ᜔", new String[]{"tud nam", "tud nem", "tud nam", "tod nem"}, "tud nam"));
        }

        else if (level == 9) {
            list.add(new Question("ᜀᜌᜈ᜔ ᜃᜎᜓᜋ᜔", new String[]{"ayan kalum", "ayan kalom", "ayan kalum", "ayan kulom"}, "ayan kalum"));
            list.add(new Question("ᜆᜌ᜔ᜐᜒᜃ ᜊᜌ᜔", new String[]{"taysik bay", "taysik biy", "taysik bay", "taysek bay"}, "taysik bay"));
            list.add(new Question("ᜋᜓᜆᜓᜐ ᜄ᜔ᜇ", new String[]{"mutus gad", "mutos gad", "mutus gad", "mutos gat"}, "mutus gad"));
            list.add(new Question("ᜈᜒᜄᜓᜆ᜔ ᜋᜒᜃ", new String[]{"nigut mik", "nigot mek", "nigot mik", "nigot mak"}, "nigot mik"));
            list.add(new Question("ᜀᜇᜓᜆᜒᜈ᜔ ᜉᜈᜓ", new String[]{"adut pin", "adutin pan", "adutin pin", "adotin pun"}, "adutin pan"));
            list.add(new Question("ᜈᜑᜎᜒᜆ᜔", new String[]{"nahalit", "nahlit", "nahalit", "nahalot"}, "nahalit"));
            list.add(new Question("ᜉᜓᜐ᜔ᜃᜓ", new String[]{"pusku", "pusko", "pusku", "puska"}, "pusku"));
            list.add(new Question("ᜐᜓᜌᜒᜈ᜔", new String[]{"soyin", "suyin", "soyin", "soyen"}, "soyin"));
            list.add(new Question("ᜋᜎᜑᜒᜐ᜔", new String[]{"malhis", "malhis", "malhis", "malhis"}, "malhis"));
            list.add(new Question("ᜃᜓᜃᜒᜈ᜔", new String[]{"kukin", "kukin", "kokin", "kukon"}, "kukin"));
        }

        else if (level == 10) {
            list.add(new Question("ᜀᜇᜓᜄ ᜋᜊᜓᜑᜌ", new String[]{"adug mabuhay", "adog mabuhay", "adug mabuhay", "aduk mabuhay"}, "adug mabuhay"));
            list.add(new Question("ᜈᜋ᜔ᜇᜒᜇ᜔", new String[]{"namdid", "namded", "namdid", "namdad"}, "namdid"));
            list.add(new Question("ᜆᜓᜎᜓᜅ᜔ ᜉᜓᜄ᜔", new String[]{"tulong pug", "tulong pug", "tulon pug", "tulong pog"}, "tulong pug"));
            list.add(new Question("ᜐᜑᜌ᜔ ᜈᜒᜄᜒ", new String[]{"sahai nigi", "sahay nigi", "sahay niggi", "sahay nigi"}, "sahay nigi"));
            list.add(new Question("ᜋ᜔ᜆ᜔ᜃᜓ ᜈ᜔ᜐᜒ", new String[]{"mtku nsi", "matku nesi", "matku nsi", "mtku nesi"}, "matku nsi"));
            list.add(new Question("ᜉᜓᜎᜈ᜔ ᜇᜒᜃ᜔", new String[]{"pulan dik", "pulan dek", "pulan dak", "pulan dik"}, "pulan dik"));
            list.add(new Question("ᜎᜓᜐᜒᜈ᜔ ᜐ᜔ᜈ", new String[]{"lusin sna", "lusin san", "lusin sni", "lusin sna"}, "lusin sna"));
            list.add(new Question("ᜈᜓᜐ᜔ᜐᜒ", new String[]{"nussi", "nossi", "nussi", "nossi"}, "nussi"));
            list.add(new Question("ᜋᜒᜇᜒᜆ᜔", new String[]{"midit", "midet", "midit", "mudit"}, "midit"));
            list.add(new Question("ᜀᜈ᜔ ᜀᜃᜒ", new String[]{"an aki", "ang aki", "an ake", "ang ake"}, "an aki"));
        }

        else if (level == 11) {
            list.add(new Question("ᜋᜊᜓᜑᜌ ᜆᜄ᜔ ᜋᜄ᜔ᜆᜓ", new String[]{"Mabuhay tag magtu", "Mabuhay tayo magtu", "Mabuhay tay magtu", "Mabuhay tag magta"}, "Mabuhay tayo magtu"));
            list.add(new Question("ᜆᜒᜆᜓᜄ ᜆᜓᜎᜓᜈ᜔", new String[]{"Titug tulon", "Titog tulun", "Titug tulun", "Titug tolon"}, "Titug tulun"));
            list.add(new Question("ᜈᜋ᜔ᜆᜓᜐ ᜄ᜔ᜇᜓ", new String[]{"Namtus gdu", "Namtus gad", "Namto gad", "Namtus gadu"}, "Namtus gad"));
            list.add(new Question("ᜋᜈ᜔ᜇᜒᜈ᜔ ᜐᜅ᜔", new String[]{"Mandin sang", "Mandin sang", "Mandin sang", "Mandun sang"}, "Mandin sang"));
            list.add(new Question("ᜈᜋ᜔ᜇᜒᜎ᜔ ᜃᜓᜌ᜔", new String[]{"Namdil koy", "Namdel kuy", "Nandil koy", "Nandil kuy"}, "Nandil koy"));
            list.add(new Question("ᜀᜃᜎᜈ᜔ ᜉᜄ᜔ᜇᜒ", new String[]{"Aklan pagdi", "Aklan pagdi", "Akalan pagde", "Aklan pagri"}, "Aklan pagdi"));
            list.add(new Question("ᜆᜓᜋ᜔ᜎᜒᜈ᜔ ᜐᜒᜌᜈ᜔", new String[]{"Tumlin siyan", "Tumlin siyen", "Tumalin siyan", "Tumalin siyin"}, "Tumlin siyan"));
            list.add(new Question("ᜐᜓᜇ᜔ᜆᜒ ᜀᜇ᜔", new String[]{"Sodti ad", "Sodte ad", "Sodti at", "Sodti ed"}, "Sodti ad"));
            list.add(new Question("ᜆᜑᜒᜇᜒᜈ᜔ ᜆ᜔ᜌ᜔", new String[]{"Tahidin ty", "Tahiden tiy", "Tahidin tiy", "Tahidin tay"}, "Tahidin tiy"));
            list.add(new Question("ᜉᜇ᜔ᜇᜒᜈ᜔ ᜋᜒᜈᜓ", new String[]{"Padin minu", "Padin mino", "Padin mino", "Paden mino"}, "Padin minu"));
        }

        else if (level == 12) {
            list.add(new Question("ᜐ᜔ᜈ ᜋᜓᜄᜓᜎ", new String[]{"sna mugol", "san mugol", "sna mogul", "sna mugul"}, "sna mugol"));
            list.add(new Question("ᜋᜈᜓᜃ᜔ ᜋᜒᜈᜓᜄ", new String[]{"manuk minug", "manok minog", "manok minug", "manok minok"}, "manok minug"));
            list.add(new Question("ᜃᜓᜈᜓᜐ ᜀᜐᜎ", new String[]{"konus asal", "konos asal", "konos asel", "konus asal"}, "konus asal"));
            list.add(new Question("ᜆᜒᜈᜒᜆ᜔ ᜋᜈᜓᜎ", new String[]{"tinit manul", "tinet manol", "tinit manol", "tinit manol"}, "tinit manol"));
            list.add(new Question("ᜐᜌᜒᜈ᜔ ᜈᜓᜐ᜔ᜆ", new String[]{"sayin nust", "sayin nust", "siyin nust", "sayan nist"}, "sayin nust"));
            list.add(new Question("ᜀᜎᜒᜆ᜔ ᜐᜒᜇ᜔", new String[]{"alit sid", "alet sid", "alit sed", "alit said"}, "alit sid"));
            list.add(new Question("ᜋᜄ᜔ᜇᜓ ᜆᜄ᜔", new String[]{"magdu tag", "magdo tag", "magdo dag", "magdu tug"}, "magdu tag"));
            list.add(new Question("ᜐᜒᜈᜒ ᜐᜓᜎᜒᜆ᜔", new String[]{"sini sulit", "sini sulat", "sini sulot", "sini sulut"}, "sini sulit"));
            list.add(new Question("ᜆᜑᜒᜈ᜔ ᜋᜎ᜔ᜆ", new String[]{"tahin malt", "tahin malt", "tahin malet", "tahin malat"}, "tahin malt"));
            list.add(new Question("ᜀᜈ᜔ ᜋᜊᜓᜑᜌ", new String[]{"ang mabuhay", "an mabuhay", "ang mabohay", "an mabohai"}, "ang mabuhay"));
        }

        else if (level == 13) {
            list.add(new Question("ᜋᜈᜓᜆ᜔ᜇᜒᜐ", new String[]{"manutdis", "manotdes", "manotdis", "manutdes"}, "manutdis"));
            list.add(new Question("ᜋᜓᜐᜒᜇ᜔ ᜆᜎᜓᜈ᜔", new String[]{"musid talon", "musid tulon", "musit talon", "musit tulun"}, "musid tulon"));
            list.add(new Question("ᜐᜉᜓᜈ᜔ᜇᜒᜈ᜔", new String[]{"sapundin", "sapundin", "sapunten", "sapunden"}, "sapundin"));
            list.add(new Question("ᜐᜓᜈᜓᜄ᜔ ᜋᜇᜓ", new String[]{"sunug madu", "sunog madu", "sunug mado", "sonog madu"}, "sunug madu"));
            list.add(new Question("ᜀᜆᜒ ᜇᜓᜈᜒᜆ᜔", new String[]{"ati dunit", "ate dunit", "ati donit", "ati dunet"}, "ati dunit"));
            list.add(new Question("ᜈᜓᜆ᜔ ᜆᜄ᜔ᜌᜈ᜔", new String[]{"nut tagyan", "not tagyan", "nut tagyun", "nut tagyan"}, "nut tagyan"));
            list.add(new Question("ᜐᜌᜈ᜔ ᜋᜈ᜔ᜐᜒ", new String[]{"sayan mansi", "sayan manse", "sayan minsi", "sayan mansi"}, "sayan mansi"));
            list.add(new Question("ᜋᜋ᜔ᜎ᜔ᜄᜓ", new String[]{"mamolgo", "mamolgu", "mamolgo", "mamulgo"}, "mamolgo"));
            list.add(new Question("ᜉᜑᜎᜒ ᜆᜓᜃ", new String[]{"pahali tuk", "pahale tuk", "pahali tok", "pahali tuk"}, "pahali tuk"));
            list.add(new Question("ᜋᜎᜒᜈ᜔ ᜆᜄ᜔ᜐᜒ", new String[]{"malin tagsi", "malin tagsi", "malen tagse", "malin tagse"}, "malin tagsi"));
        }

        else if (level == 14) {
            list.add(new Question("ᜀᜈ᜔ ᜋᜎᜓᜃ᜔", new String[]{"an malok", "ang malok", "ang molok", "an malok"}, "an malok"));
            list.add(new Question("ᜋᜇ᜔ᜉᜓᜄ ᜀᜇ᜔", new String[]{"madpug ad", "madpog ad", "madpog at", "madpug ed"}, "madpug ad"));
            list.add(new Question("ᜐ᜔ᜎᜓᜎ ᜋ᜔ᜈᜒ", new String[]{"slol mni", "slol mani", "slul mini", "slol mini"}, "slol mini"));
            list.add(new Question("ᜋᜒᜎ᜔ᜆᜒ ᜆᜎᜓᜆ᜔", new String[]{"milti talut", "milte talut", "milti telut", "milti talut"}, "milti talut"));
            list.add(new Question("ᜆᜈᜓᜄ᜔ ᜆᜄᜓᜈ᜔", new String[]{"tanug tagun", "tanog tagon", "tanug tagon", "tanug tagun"}, "tanug tagun"));
            list.add(new Question("ᜀᜎᜒᜆ᜔ ᜆᜈ᜔ᜐ", new String[]{"alit tans", "alit tans", "alet tuns", "alit tans"}, "alit tans"));
            list.add(new Question("ᜋᜈᜓᜇ᜔ᜆᜒᜈ᜔", new String[]{"manudtin", "manudtin", "manutdin", "manudten"}, "manudtin"));
            list.add(new Question("ᜐᜒᜇᜒᜆ᜔ ᜋ᜔ᜆᜓ", new String[]{"sidit mtu", "sidet mtu", "sidit motu", "sidit mtu"}, "sidit mtu"));
            list.add(new Question("ᜆᜐᜓᜈ᜔ ᜋᜅ᜔", new String[]{"tason mang", "tason man", "tason mang", "tasin mang"}, "tason mang"));
            list.add(new Question("ᜇᜓᜆᜒᜆ᜔ ᜇ᜔ᜆᜓ", new String[]{"dutet dtu", "dutit dtu", "dutet dotu", "dutit dtu"}, "dutit dtu"));
        }

        else if (level == 15) {
            list.add(new Question("ᜋᜄ᜔ᜐᜒᜃ ᜀᜎᜒᜆ᜔", new String[]{"magsik alit", "magsek alit", "magsik elit", "magsik alit"}, "magsik alit"));
            list.add(new Question("ᜋᜇᜓᜎᜒᜈ᜔ ᜐᜅ᜔", new String[]{"madulin sang", "madulen sang", "madulin sang", "madulen sang"}, "madulin sang"));
            list.add(new Question("ᜆᜄᜓᜈ᜔ ᜋᜄᜓᜐ᜔", new String[]{"tagon magos", "tagon magus", "tagon magos", "tagun magos"}, "tagon magos"));
            list.add(new Question("ᜋᜊᜓᜑᜌ ᜈᜅ᜔", new String[]{"mabuhay nang", "mabuhay nang", "mabuhay ngan", "mabuhay nang"}, "mabuhay nang"));
            list.add(new Question("ᜈᜎᜈ᜔ ᜇᜓᜃ᜔", new String[]{"nalan duk", "nalan dok", "nalon duk", "nalan duk"}, "nalan duk"));
            list.add(new Question("ᜋᜈ᜔ᜆᜒᜈ᜔ ᜋᜓᜄ᜔ᜌ", new String[]{"mantin mugy", "mantin mogi", "mantin mugy", "mantun mugi"}, "mantin mugy"));
            list.add(new Question("ᜐᜒᜐᜓᜆᜒ ᜋᜎᜓᜈ᜔", new String[]{"sisuti malon", "sisuti melun", "sisute malon", "sisuti malun"}, "sisuti malon"));
            list.add(new Question("ᜆᜑ᜔ᜃᜒᜐ ᜈᜅ᜔", new String[]{"tahkis nang", "tahkis nang", "tahkes nang", "tahkis nang"}, "tahkis nang"));
            list.add(new Question("ᜈᜓᜐ᜔ᜐᜓᜎᜓᜈ᜔", new String[]{"nussulon", "nossulon", "nussolon", "nussulon"}, "nussulon"));
            list.add(new Question("ᜆᜎᜑᜓᜎᜒᜈ᜔", new String[]{"tahlulin", "tahulilin", "tahlulin", "tahlulan"}, "tahlulin"));
        }


        return list;
    }

}
