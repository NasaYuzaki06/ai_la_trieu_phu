package src;

import java.util.List;
import java.util.Random;

public class PhoneAFriend implements LifeLine {
    private final String name = "Phone a friend";
    private boolean isUsed = false;

    public int getCorrectAnswerIndex(Question currentQuestion) {
        List<Answer> answer = currentQuestion.getAnswer();
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).isCorrectAnswer()) {
                return i;
            }
        }
        return -1;
    }

    public int getRateByLevel(int currentLevel) {
        int success = 0;
        if (currentLevel == 5) {
            success = 80;
            return success;
        } else if (currentLevel == 10) {
            success = 50;
            return success;
        } else {
            success = 20;
            return success;
        }
    }

    @Override
    public void use(Question question, GameUI gameUI, Player player) {
        Random random = new Random();
        int randomRate = random.nextInt(101);
        if (randomRate <= getRateByLevel(player.getCurrentLevel())) {
            System.out.println("Người thân của bạn đã đưa ra đáp án " + question.getAnswer().get(getCorrectAnswerIndex(question)).getOptionIdentifier());
        } else {
            for (Answer ans : question.getAnswer()) {
                if (!ans.isCorrectAnswer()) {
                    System.out.println("Người thân của bạn đã đưa ra đáp án: " + ans.getOptionIdentifier());
                }
            }
        }
        this.isUsed = true;
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