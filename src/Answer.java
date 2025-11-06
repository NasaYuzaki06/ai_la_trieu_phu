package src;

public class Answer {
    private String answerContent;
    private boolean correctAnswer;
    private char optionIdentifier;

    public Answer(String answerContent, boolean correctAnswer, char optionIdentifier) {
        this.answerContent = answerContent;
        this.correctAnswer = correctAnswer;
        this.optionIdentifier = optionIdentifier;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public char getOptionIdentifier() {
        return optionIdentifier;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setOptionIdentifier(char optionIdentifier) {
        this.optionIdentifier = optionIdentifier;
    }
}