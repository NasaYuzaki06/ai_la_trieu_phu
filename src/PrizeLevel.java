package src;

public class PrizeLevel {
    private int questionNumber;
    private long prizeValue;
    private boolean isSafeHaven;

    public PrizeLevel(int questionNumber, long prizeValue, boolean isSafeHaven) {
        this.questionNumber = questionNumber;
        this.prizeValue = prizeValue;
        this.isSafeHaven = isSafeHaven;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public long getPrizeValue() {
        return prizeValue;
    }

    public boolean isSafeHaven() {
        return isSafeHaven;
    }
}