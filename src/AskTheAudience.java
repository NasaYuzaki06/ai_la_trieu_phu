package src;

import java.util.*;

public class AskTheAudience implements LifeLine {
    private final String name = "Ask the audience";
    private boolean isUsed = false;

    public int[] getWeightByLevel(int correctAnswerIndex, int currentLevel) {
        if (correctAnswerIndex < 0 || correctAnswerIndex > 3) {
            throw new IllegalArgumentException("Chỉ số đáp án không hợp lệ!");
        }
        Random random = new Random();
        int[] weights = new int[4];
        if (currentLevel == 5) {
            weights[0] = 5 + random.nextInt(11);
            weights[1] = 5 + random.nextInt(11);
            weights[2] = 5 + random.nextInt(11);
            weights[3] = 5 + random.nextInt(11);
            weights[correctAnswerIndex] = 60 + random.nextInt(11);
        } else if (currentLevel == 10) {
            weights[0] = 5 + random.nextInt(12);
            weights[1] = 5 + random.nextInt(12);
            weights[2] = 5 + random.nextInt(12);
            weights[3] = 5 + random.nextInt(12);
            weights[correctAnswerIndex] = 40 + random.nextInt(11);
        } else {
            weights[0] = 5 + random.nextInt(16);
            weights[1] = 5 + random.nextInt(16);
            weights[2] = 5 + random.nextInt(16);
            weights[3] = 5 + random.nextInt(16);
            weights[correctAnswerIndex] = 20 + random.nextInt(11);
        }
        int[] pollResult = new int[4];
        for (int i = 1; i <= 100; i++) {
            int vote = getVoteIndex(weights);
            pollResult[vote]++;
        }
        return pollResult;
    }

    public int getVoteIndex(int[] weights) {
        int totalWeight = 0;
        for (int w : weights) {
            totalWeight += w;
        }
        Random random = new Random();
        int randomWeight = random.nextInt(totalWeight);
        int cumulativeWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (randomWeight < cumulativeWeight) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void use(Question currentQuestion, GameUI gameUI, Player player) {
        int currentCorrectIndexAnswer = 0;
        for (int i = 0; i < currentQuestion.getAnswer().size(); i++) {
            if (currentQuestion.getAnswer().get(i).isCorrectAnswer()) {
                currentCorrectIndexAnswer = i;
            }
        }
        int[] answerRate = getWeightByLevel(currentCorrectIndexAnswer, player.getCurrentLevel());
        gameUI.setAudiencePollResult(answerRate);
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