package com.example.baybayinquest;

public class Question {
    private final String symbol;
    private final String[] choices;
    private final String correctAnswer;

    public Question(String symbol, String[] choices, String correctAnswer) {
        this.symbol = symbol;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getSymbol() {
        return symbol;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
