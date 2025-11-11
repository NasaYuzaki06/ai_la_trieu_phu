package src;

import java.util.ArrayList;
import java.util.List;

public class FiftyFifty implements LifeLine {
    private final String name = "50:50";
    private boolean isUsed = false;

    @Override
    public void use(Question question, GameUI gameUI, Player player) {
        List<Answer> wrongAnswers = new ArrayList<>();
        for (Answer answer : question.getAnswer()) {
            if (!answer.isCorrectAnswer()) {
                wrongAnswers.add(answer);
            }
        }
        if (wrongAnswers.size() >= 2) {
            Answer answerToRemove1 = wrongAnswers.get(0);
            Answer answerToRemove2 = wrongAnswers.get(1);
            question.getAnswer().removeIf(ans ->
                    ans.equals(answerToRemove1) || ans.equals(answerToRemove2)
            );

            this.isUsed = true;
            gameUI.setLifelineMessage("Hai phương án sai đã được loại bỏ.");
        }
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public boolean isUsed() {
        return this.isUsed;
    }
}