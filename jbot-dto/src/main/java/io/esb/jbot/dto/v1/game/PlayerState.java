package io.esb.jbot.dto.v1.game;

public class PlayerState extends Player {
    private long score;
    private int attemptedCount;
    private int correctCount;

    public PlayerState(Player player) {
        super(player.getProvider(), player.getTeam(), player.getUsername());
    }

    public void correctAnswer(int value) {
        this.score += value;
        this.attemptedCount += 1;
        this.correctCount += 1;
    }

    public void incorrectAnswer(int value) {
        this.score -= value;
        this.attemptedCount += 1;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getAttemptedCount() {
        return attemptedCount;
    }

    public void setAttemptedCount(int attemptedCount) {
        this.attemptedCount = attemptedCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }
}
