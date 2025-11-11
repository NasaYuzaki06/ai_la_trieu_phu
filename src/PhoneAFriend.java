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
            success = 90;
            return success;
        } else if (currentLevel == 10) {
            success = 60;
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
        String friendMessage;
        if (randomRate <= getRateByLevel(player.getCurrentLevel())) {
            char correctOption = question.getAnswer().get(getCorrectAnswerIndex(question)).getOptionIdentifier();
            friendMessage = "Người thân của bạn rất chắc chắn! Họ chọn đáp án " + correctOption;
        } else {
            Answer wrongAnswer = null;
            for (Answer ans : question.getAnswer()) {
                if (!ans.isCorrectAnswer()) {
                    wrongAnswer = ans;
                    break;
                }
            }
            if (wrongAnswer != null) {
                friendMessage = "Người thân của bạn hơi phân vân và đoán là đáp án: " + wrongAnswer.getOptionIdentifier();
            } else {
                friendMessage = "Người thân của bạn không biết câu trả lời!";
            }
        }
        gameUI.setLifelineMessage(friendMessage);
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