package com.example.baybayinquest;

public class ScoreTracker {
    private int score;
    private int streak;
    private static final int BASE_POINTS = 10;
    private static final int STREAK_BONUS = 5;

    public ScoreTracker() {
        score = 0;
        streak = 0;
    }

    // Update score for a correct answer
    public void recordCorrectAnswer() {
        streak++;
        score += BASE_POINTS + (streak >= 3 ? STREAK_BONUS * (streak / 3) : 0);
    }

    // Reset streak on wrong answer
    public void recordWrongAnswer() {
        streak = 0;
    }

    // Getters
    public int getScore() {
        return score;
    }

    public int getStreak() {
        return streak;
    }

    // Reset the tracker
    public void reset() {
        score = 0;
        streak = 0;
    }
}