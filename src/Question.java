package src;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionContent;
    private List<Answer> answer;
    private int difficultyLevel;

    public Question(String questionContent, List<Answer> answer, int difficultyLevel) {
        this.questionContent = questionContent;
        this.answer = answer;
        this.difficultyLevel = difficultyLevel;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}